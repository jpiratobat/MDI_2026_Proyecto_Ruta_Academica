import { useState } from "react";
import "./DetallePlan.css";
import Resumen from "./Resumen";
import Materias from "./Materias";
import Prerrequisitos from "./Prerrequisitos";
import Configuracion from "./Configuracion";
import Simulacion from "./Simulacion";
import { Outlet, useLocation, useNavigate, useParams } from "react-router-dom";

function DetallePlan() {
  const [tab, setTab] = useState("Resumen");
  const location = useLocation();
  const navigate = useNavigate();
  const { id: planId } = useParams();

  // Detect if we are using nested routes under /plan/... (demo mode) or /detalle-plan/:id/... (URL mode)
  const nestedMode = location.pathname.startsWith("/plan/") || location.pathname.startsWith("/detalle-plan/");

  // activeTab: when in nestedMode derive from pathname, otherwise use local state
  const activeTab = nestedMode
    ? (() => {
        const seg = location.pathname.split('/').filter(Boolean).pop();
        const label = seg ? seg.charAt(0).toUpperCase() + seg.slice(1) : "Resumen";
        return ["Resumen", "Materias", "Prerrequisitos", "Configuracion", "Simulacion"].includes(label)
          ? label
          : "Resumen";
      })()
    : tab;

  const handleTabClick = (target) => {
    if (nestedMode) {
      navigate(target.toLowerCase());
    } else {
      setTab(target);
    }
  };

  return (
    <div className="DetallePlan">
      <div className="tabs">

        <button
        className={`tab-button ${activeTab === "Resumen" ? "active" : ""}`}
        onClick={() => handleTabClick("Resumen")}>
          Resumen
        </button>

        <button
        className={`tab-button ${activeTab === "Materias" ? "active" : ""}`}
        onClick={() => handleTabClick("Materias")}>
          Materias
        </button>

        <button
        className={`tab-button ${activeTab === "Prerrequisitos" ? "active" : ""}`}
        onClick={() => handleTabClick("Prerrequisitos")}>
          Prerrequisitos
        </button>

        <button
        className={`tab-button ${activeTab === "Configuracion" ? "active" : ""}`}
        onClick={() => handleTabClick("Configuracion")}>
          Configuración
        </button>

        <button
        className={`tab-button ${activeTab === "Simulacion" ? "active" : ""}`}
        onClick={() => handleTabClick("Simulacion")}>
          Simulación
        </button>
      </div>

      <div className="tab-content">
        {nestedMode ? (
          <Outlet />
        ) : (
          <>
            {activeTab === "Resumen" && <Resumen planId={planId} />}
            {activeTab === "Materias" && <Materias planId={planId} />}
            {activeTab === "Prerrequisitos" && <Prerrequisitos planId={planId} />}
            {activeTab === "Configuracion" && <Configuracion planId={planId} />}
            {activeTab === "Simulacion" && <Simulacion planId={planId} />}
          </>
        )}
      </div>
    </div>
  );
}

export default DetallePlan;