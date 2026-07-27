import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import DetallePlan from "../pages/DetallePlan";
import Resumen from "../pages/Resumen";
import Materias from "../pages/Materias";
import Prerrequisitos from "../pages/Prerrequisitos";
import Configuracion from "../pages/Configuracion";
import Simulacion from "../pages/Simulacion";


function App() {
  return (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Home />} />
      {/* Ruta compatibilidad: Detalle por id (usada desde Home) */}
      <Route path="/detalle-plan/:id" element={<DetallePlan />}>
        <Route index element={<Resumen />} />
        <Route path="resumen" element={<Resumen />} />
        <Route path="materias" element={<Materias />} />
        <Route path="prerrequisitos" element={<Prerrequisitos />} />
        <Route path="configuracion" element={<Configuracion />} />
        <Route path="simulacion" element={<Simulacion />} />
      </Route>
      <Route path="/plan" element={<DetallePlan />}>
        <Route index element={<Resumen planId="demo" />} />
        <Route path="resumen" element={<Resumen planId="demo" />} />
        <Route path="materias" element={<Materias planId="demo" />} />
        <Route path="prerrequisitos" element={<Prerrequisitos planId="demo" />} />
        <Route path="configuracion" element={<Configuracion planId="demo" />} />
        <Route path="simulacion" element={<Simulacion planId="demo" />} />
      </Route>
    </Routes>
  </BrowserRouter>
  );
}

export default App;
