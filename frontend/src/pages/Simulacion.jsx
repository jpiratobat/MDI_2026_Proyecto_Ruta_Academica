import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./Simulacion.css";

const API_BASE_URL = "http://localhost:8088/api";

export default function Simulacion({ planId }) {
  const params = useParams();
  const actualPlanId = planId || params.id || null;
  const [maxMaterias, setMaxMaterias] = useState(5);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [note, setNote] = useState("");

  // Cargar preferencia de materias por semestre desde localStorage
  useEffect(() => {
    if (!actualPlanId) return;
    try {
      const raw = localStorage.getItem(`config_plan_${actualPlanId}`);
      if (raw) {
        const cfg = JSON.parse(raw);
        if (cfg.materiasPorSemestre) setMaxMaterias(cfg.materiasPorSemestre);
      }
    } catch {
      // ignore
    }
  }, [actualPlanId]);

  const handleCalcular = async () => {
    if (!actualPlanId) {
      setNote("Selecciona un plan para calcular sus semestres.");
      return;
    }
    try {
      setLoading(true);
      setResult(null);
      setNote("");

      const res = await fetch(`${API_BASE_URL}/planes/${actualPlanId}/semestres?max=${maxMaterias}`);
      const data = await res.json();

      if (res.status === 409 || data.hasCycle) {
        setNote("El plan tiene dependencias cíclicas, no se puede calcular.");
        setResult(data);
        return;
      }

      if (!res.ok) throw new Error("Error en simulación");

      setResult(data);
    } catch (e) {
      console.error(e);
      setNote("Error al calcular semestres");
    } finally {
      setLoading(false);
    }
  };

  if (!actualPlanId) {
    return (
      <div className="Simulacion page-placeholder">
        <h3>Simulación</h3>
        <p>Selecciona un plan para calcular su distribución de semestres.</p>
      </div>
    );
  }

  return (
    <div className="Simulacion">
      <h2>Simulación de semestres</h2>
      <div className="sim-controls">
        <label>
          Máx materias por semestre:
          <input
            type="number"
            min={1}
            value={maxMaterias}
            onChange={(e) => setMaxMaterias(Number(e.target.value) || 1)}
          />
        </label>
        <button className="btn-calc" onClick={handleCalcular} disabled={loading}>
          {loading ? "Calculando..." : "Calcular"}
        </button>
      </div>

      {note && <div className="error-note">{note}</div>}

      {result && (
        <div className="semestres-container">
          <div className="result-summary">
            <span>Mínimo de semestres: {result.minSemesters}</span>
            {result.hasCycle && <span className="cycle-flag">Plan con ciclos</span>}
          </div>
          <div className="semestres-grid">
            {Array.isArray(result.semesters) && result.semesters.length > 0 ? (
              result.semesters.map((sem) => (
                <div key={sem.semesterNumber} className="semestre-card">
                  <div className="semestre-header">
                    <h4>Semestre {sem.semesterNumber}</h4>
                    <span className="materias-count">
                      {sem.materias ? sem.materias.length : 0} materias
                    </span>
                  </div>
                  <div className="semestre-materias">
                    {Array.isArray(sem.materias) && sem.materias.length > 0 ? (
                      sem.materias.map((m) => (
                        <div key={m.idMateria} className="materia-item">
                          <span className="materia-code">{m.nomMateria}</span>
                          <span className="materia-credits">{m.numCreditos} cred</span>
                        </div>
                      ))
                    ) : (
                      <div className="empty-msg">Sin materias en este semestre</div>
                    )}
                  </div>
                </div>
              ))
            ) : (
              <div className="empty-msg">No hay semestres calculados</div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
