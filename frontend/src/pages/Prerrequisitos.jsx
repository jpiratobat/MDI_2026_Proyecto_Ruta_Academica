import { useEffect, useMemo, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import "./Prerrequisitos.css";

const API_BASE_URL = "http://localhost:8088/api";

export default function Prerrequisitos({ planId }) {
  const params = useParams();
  const location = useLocation();
  const actualPlanId = planId ?? params.id ?? location.state?.planId ?? null;

  const [relations, setRelations] = useState([]);
  const [materias, setMaterias] = useState([]);
  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState({ idMateria: "", idPrerequisito: "" });
  const [note, setNote] = useState("");

  useEffect(() => {
    if (!actualPlanId) return;
    fetchAll();
  }, [actualPlanId]);

  const fetchAll = async () => {
    try {
      setLoading(true);
      const [rRes, mRes] = await Promise.all([
        fetch(`${API_BASE_URL}/prerequisitos/plan/${actualPlanId}`),
        fetch(`${API_BASE_URL}/planes/${actualPlanId}/materias`),
      ]);
      if (!rRes.ok) throw new Error("Error cargando prerrequisitos");
      if (!mRes.ok) throw new Error("Error cargando materias");
      const rData = await rRes.json();
      const mData = await mRes.json();

      const mappedMaterias = Array.isArray(mData)
        ? mData.map((m, idx) => ({
            id: m.idMateria ?? idx,
            nombre: m.nomMateria ?? m.nombre ?? "-",
            creditos: m.numCreditos ?? m.creditos ?? 0,
          }))
        : [];

      setRelations(Array.isArray(rData) ? rData : []);
      setMaterias(mappedMaterias);
      setNote("");
    } catch (e) {
      console.error(e);
      setNote("No se pudieron cargar datos.");
    } finally {
      setLoading(false);
    }
  };

  const handleAdd = async () => {
    if (!form.idMateria || !form.idPrerequisito) {
      setNote("Selecciona materia y prerequisito.");
      return;
    }
    try {
      const res = await fetch(`${API_BASE_URL}/prerequisitos`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          idMateria: Number(form.idMateria),
          idPrerequisito: Number(form.idPrerequisito),
        }),
      });
      if (!res.ok) throw new Error("Error creando relación");
      setNote("Relación creada");
      setForm({ idMateria: "", idPrerequisito: "" });
      fetchAll();
    } catch (e) {
      console.error(e);
      setNote("Error al crear relación");
    }
  };

  const handleDelete = async (idM, idP) => {
    if (!window.confirm("Eliminar relación?")) return;
    try {
      const res = await fetch(`${API_BASE_URL}/prerequisitos/${idM}/${idP}`, { method: "DELETE" });
      if (!res.ok) throw new Error("Error eliminando relación");
      setNote("Relación eliminada");
      fetchAll();
    } catch (e) {
      console.error(e);
      setNote("Error al eliminar relación");
    }
  };

  const materiasMap = useMemo(
    () => Object.fromEntries(materias.map((m) => [String(m.id), m])),
    [materias]
  );

  const grouped = useMemo(() => {
    const bucket = {};
    relations.forEach((r) => {
      const key = String(r.idMateria);
      if (!bucket[key]) bucket[key] = [];
      bucket[key].push(r);
    });
    return bucket;
  }, [relations]);

  if (!actualPlanId) {
    return (
      <div className="Prerrequisitos page-placeholder">
        <h3>Prerrequisitos</h3>
        <p>Selecciona un plan para ver relaciones.</p>
      </div>
    );
  }

  return (
    <div className="Prerrequisitos">
      <h3>Prerrequisitos del plan</h3>
      {note && <div className="note">{note}</div>}
      {loading ? (
        <div>Cargando...</div>
      ) : (
        <div className="relations-list">
          {Object.keys(grouped).length === 0 && <div>No hay relaciones registradas</div>}
          {Object.entries(grouped).map(([idMateria, prereqList]) => {
            const target = materiasMap[idMateria] || { nombre: idMateria };
            return (
              <div key={idMateria} className="relation-card">
                <div className="relation-materia">
                  <div className="name">{target.nombre}</div>
                  <div className="code">ID: {idMateria}</div>
                </div>

                <div className="relation-prereqs">
                  {prereqList.map((rel) => (
                    <span className="prereq-badge" key={`${rel.idMateria}-${rel.idPrerequisito}`}>
                      {rel.nomPrerequisito ?? rel.idPrerequisito}
                      <button
                        className="btn-del-small"
                        onClick={() => handleDelete(rel.idMateria, rel.idPrerequisito)}
                        title="Eliminar relación"
                      >
                        ×
                      </button>
                    </span>
                  ))}
                </div>
              </div>
            );
          })}
        </div>
      )}

      <div className="add-relation">
        <h4>Agregar relación</h4>
        <select value={form.idMateria} onChange={(e) => setForm({ ...form, idMateria: e.target.value })}>
          <option value="">Seleccionar materia</option>
          {materias.map((m) => (
            <option key={m.id} value={m.id}>
              {m.nombre} (ID: {m.id})
            </option>
          ))}
        </select>
        <select value={form.idPrerequisito} onChange={(e) => setForm({ ...form, idPrerequisito: e.target.value })}>
          <option value="">Seleccionar prerrequisito</option>
          {materias.map((m) => (
            <option key={m.id} value={m.id}>
              {m.nombre} (ID: {m.id})
            </option>
          ))}
        </select>
      </div>
      <div className="add-actions">
        <button className="btn-add" onClick={handleAdd}>Agregar</button>
      </div>
    </div>
  );
}
