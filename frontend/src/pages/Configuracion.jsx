import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./Configuracion.css";

export default function Configuracion({ planId }) {
  const params = useParams();
  const actualPlanId = planId || params.id || null;
  const [materiasPorSemestre, setMateriasPorSemestre] = useState(5);
  const [creditosPorSemestre, setCreditosPorSemestre] = useState(20);

  // load saved config from localStorage per plan
  useEffect(() => {
    if (!actualPlanId) return;
    try {
      const raw = localStorage.getItem(`config_plan_${actualPlanId}`);
      if (raw) {
        const cfg = JSON.parse(raw);
        if (cfg.materiasPorSemestre) setMateriasPorSemestre(cfg.materiasPorSemestre);
        if (cfg.creditosPorSemestre) setCreditosPorSemestre(cfg.creditosPorSemestre);
      }
    } catch (e) { /* ignore */ }
  }, [actualPlanId]);

  const handleSave = () => {
    if (!actualPlanId) return alert("No hay plan seleccionado");
    const cfg = { materiasPorSemestre, creditosPorSemestre };
    localStorage.setItem(`config_plan_${actualPlanId}`, JSON.stringify(cfg));
    // alert("Configuración guardada en localStorage (no persistida en backend)");
  };

  return (
    <div className="Configuracion">
      <h3>Configuración del plan</h3>
      {!planId && <div className="note">Sin plan seleccionado. Puedes guardar configuración por plan usando un `planId`.</div>}

      <div className="config-row">
        <label>Materias por semestre:</label>
        <input type="number" min={1} value={materiasPorSemestre} onChange={e => setMateriasPorSemestre(Number(e.target.value) || 1)} />
      </div>

      {/* <div className="config-row">
        <label>Créditos por semestre:</label>
        <input type="number" min={1} value={creditosPorSemestre} onChange={e => setCreditosPorSemestre(Number(e.target.value) || 1)} />
      </div> */}

      <div className="config-actions">
        <button onClick={handleSave} className="btn-save">Guardar</button>
      </div>

      <div className="explain">
        <p>Nota: Actualmente el backend solo utiliza el parámetro <code>Materias por semestre</code> (máx materias por semestre) en el cálculo de semestres. </p>
      </div>
    </div>
  );
}
