import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import "./Resumen.css";

const API_BASE_URL = "http://localhost:8088/api";

export default function Resumen({ planId }) {
  const params = useParams();
  const location = useLocation();
  const actualPlanId = planId ?? params.id ?? location.state?.planId ?? null;
  const [plan, setPlan] = useState(null);
  const [materias, setMaterias] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!actualPlanId) return;

    const fetchData = async () => {
      try {
        setLoading(true);
        const [planRes, materiasRes, creditosRes] = await Promise.all([
          fetch(`${API_BASE_URL}/planes/${actualPlanId}`),
          fetch(`${API_BASE_URL}/planes/${actualPlanId}/materias`),
          fetch(`${API_BASE_URL}/planes/creditos`)
        ]);

        if (!planRes.ok) throw new Error("Error al obtener plan");
        const planData = await planRes.json();
        const materiasData = materiasRes.ok ? await materiasRes.json() : [];
        const creditosData = creditosRes.ok ? await creditosRes.json() : [];

        const creditosLookup = Array.isArray(creditosData)
          ? creditosData.reduce((acc, item) => {
              const key = item.nomPlan ?? item.nombre;
              if (key) acc[key] = item.totalCreditos ?? item.creditos ?? 0;
              return acc;
            }, {})
          : {};

        const mappedMaterias = Array.isArray(materiasData)
          ? materiasData.map((m, idx) => ({
              id: m.idMateria ?? idx,
              nombre: m.nomMateria ?? m.nombre ?? "-",
              creditos: m.numCreditos ?? m.creditos ?? 0,
            }))
          : [];

        const nombrePlan = planData.nomPlan ?? planData.nombre ?? "-";
        setPlan({
          id: planData.idPlan ?? planData.id,
          nombre: nombrePlan,
          codigo: planData.codigo ?? planData.idPlan ?? planData.id ?? "-",
          creditos: creditosLookup[nombrePlan] ?? planData.creditos ?? planData.creditosTotales ?? "-",
        });
        setMaterias(mappedMaterias);
      } catch (e) {
        console.error(e);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [actualPlanId]);

  if (!actualPlanId) {
    return (
      <div className="Resumen">
        <div className="placeholder">Selecciona un plan para ver el resumen.</div>
      </div>
    );
  }

  return (
    <div className="Resumen">
      <div className="resumen-header">
        <h2>Resumen del plan</h2>
        <div className="plan-ident">
          <div className="plan-name">{plan ? plan.nombre : "-"}</div>
          <div className="plan-code">{plan ? plan.codigo : "-"}</div>
        </div>
      </div>

      <div className="resumen-body">
        <div className="stats">
          <div className="stat-card">
            <div className="stat-label">Créditos totales</div>
            <div className="stat-value">
              {plan?.creditos ?? "-"}
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-label">Materias totales</div>
            <div className="stat-value">{materias.length}</div>
          </div>

          <div className="stat-card">
            <div className="stat-label">Código</div>
            <div className="stat-value">{plan?.codigo || "-"}</div>
          </div>
        </div>

        <div className="materias-list">
          <h3>Materias del plan</h3>
          {materias.length === 0 ? (
            <div className="empty-msg">No se encontraron materias</div>
          ) : (
            <ul>
              {materias.map((m) => (
                <li key={m.id}>
                  <span className="mat-nombre">{m.nombre}</span>
                  <span className="mat-creditos">{m.creditos} créditos</span>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </div>
  );
}
