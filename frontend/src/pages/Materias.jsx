import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import "./Materias.css";

const API_BASE_URL = "http://localhost:8088/api";

export default function Materias({ planId }) {
  const params = useParams();
  const location = useLocation();
  const actualPlanId = planId ?? params.id ?? location.state?.planId ?? null;

  const [materias, setMaterias] = useState([]);
  const [loading, setLoading] = useState(false);
  const [newMateria, setNewMateria] = useState({ nombre: "", creditos: 0 });
  const [note, setNote] = useState("");

  useEffect(() => {
    if (!actualPlanId) return;
    fetchMaterias();
  }, [actualPlanId]);

  const fetchMaterias = async () => {
    try {
      setLoading(true);
      const res = await fetch(`${API_BASE_URL}/planes/${actualPlanId}/materias`);
      if (!res.ok) throw new Error("Error al obtener materias");
      const data = await res.json();
      const mapped = Array.isArray(data)
        ? data.map((m, idx) => ({
            id: m.idMateria ?? idx,
            nombre: m.nomMateria ?? m.nombre ?? "-",
            creditos: m.numCreditos ?? m.creditos ?? 0,
          }))
        : [];
      setMaterias(mapped);
      setNote("");
    } catch (e) {
      console.error(e);
      setNote("No se pudieron cargar materias.");
    } finally {
      setLoading(false);
    }
  };

  const handleCreateMateria = async () => {
    if (!newMateria.nombre.trim()) {
      setNote("Completa el nombre.");
      return;
    }
    try {
      const res = await fetch(`${API_BASE_URL}/materias`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          nomMateria: newMateria.nombre,
          numCreditos: Number(newMateria.creditos) || 0,
        }),
      });
      if (!res.ok) throw new Error("Error creando materia");
      const created = await res.json();
      setNote("Materia creada.");

      if (actualPlanId) {
        const asRes = await fetch(`${API_BASE_URL}/planes/${actualPlanId}/materias/${created.idMateria}`, { method: "POST" });
        if (!asRes.ok) throw new Error("Error asignando materia al plan");
        setNote("Materia creada y asignada al plan.");
      }
      setNewMateria({ nombre: "", creditos: 0 });
      fetchMaterias();
    } catch (e) {
      console.error(e);
      setNote("Error al crear/asignar materia.");
    }
  };

  const handleRemoveMateriaFromPlan = async (idMateria) => {
    if (!window.confirm("Desasignar materia del plan?")) return;
    try {
      const res = await fetch(`${API_BASE_URL}/planes/${actualPlanId}/materias/${idMateria}`, { method: "DELETE" });
      if (!res.ok) throw new Error("Error desasignando materia");
      setNote("Materia desasignada");
      fetchMaterias();
    } catch (e) {
      console.error(e);
      setNote("Error al desasignar materia");
    }
  };

  if (!actualPlanId) {
    return (
      <div className="Materias page-placeholder">
        <h3>Materias</h3>
        <p>Selecciona un plan para ver y editar sus materias.</p>
      </div>
    );
  }

  return (
    <div className="Materias">
      <h3>Materias del plan</h3>
      {note && <div className="note">{note}</div>}
      {loading ? (
        <div>Cargando...</div>
      ) : (
        <div className="materias-list">
          {materias.length === 0 && <div>No hay materias asignadas</div>}
          {materias.map((m) => (
            <div key={m.id} className="materia-card">
              <div className="materia-left">
                <div className="materia-name">{m.nombre}</div>
                <div className="materia-code">ID: {m.id}</div>
              </div>
              <div className="materia-right">
                <div className="materia-credits-mat">{m.creditos} cr</div>
                <button className="btn-remove" onClick={() => handleRemoveMateriaFromPlan(m.id)}>Desasignar</button>
              </div>
            </div>
          ))}
        </div>
      )}

      <div className="create-materia">
        <h4>Crear nueva materia</h4>
        <input
          placeholder="Nombre"
          value={newMateria.nombre}
          onChange={(e) => setNewMateria({ ...newMateria, nombre: e.target.value })}
        />
        <input
          type="number"
          placeholder="Créditos"
          value={newMateria.creditos}
          onChange={(e) => setNewMateria({ ...newMateria, creditos: Number(e.target.value) })}
        />
        <div className="create-actions">
          <button className="btn-create" onClick={handleCreateMateria}>Crear</button>
        </div>
      </div>
    </div>
  );
}
