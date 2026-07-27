package com.graduacionunal.backend.services;

import java.util.List;
import com.graduacionunal.backend.dto.PlanCreditosDTO;
import com.graduacionunal.backend.models.PlanEstudio;
import com.graduacionunal.backend.dto.MateriaDTO;
import com.graduacionunal.backend.datastructures.KahnSemesterCalculator;
import com.graduacionunal.backend.dto.MateriaPlanDTO;

public interface PlanEstudioService {
    List<PlanEstudio> obtenerTodos();
    PlanEstudio guardar(PlanEstudio planEstudio);
    List<PlanCreditosDTO> obtenerCreditosTotalesPorPlan();
    PlanEstudio obtenerPorId(Integer idPlanEstudio);
    PlanEstudio eliminarPorId(Integer idPlanEstudio);
    List<MateriaDTO> obtenerMateriasDePlanEstudio(Integer idPlanEstudio);
    KahnSemesterCalculator.SemesterPlan calcularSemestres(Integer idPlanEstudio, int maxMateriasPorSemestre);
    MateriaPlanDTO asignarMateriaAPlan(Integer idPlanEstudio, Integer idMateria);
    void desasignarMateriaDePlan(Integer idPlanEstudio, Integer idMateria);
}
