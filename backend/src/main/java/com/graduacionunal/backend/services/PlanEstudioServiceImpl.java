package com.graduacionunal.backend.services;

import com.graduacionunal.backend.datastructures.KahnSemesterCalculator;
import com.graduacionunal.backend.dto.MateriaDTO;
import com.graduacionunal.backend.dto.MateriaPlanDTO;
import com.graduacionunal.backend.dto.PlanCreditosDTO;
import com.graduacionunal.backend.exceptions.ResourceNotFoundException;
import com.graduacionunal.backend.models.Materia;
import com.graduacionunal.backend.models.MateriaPorPlan;
import com.graduacionunal.backend.models.MateriaPorPlanId;
import com.graduacionunal.backend.models.PlanEstudio;
import com.graduacionunal.backend.models.Prerequisito;
import com.graduacionunal.backend.repositories.MateriaPorPlanRepository;
import com.graduacionunal.backend.repositories.MateriaRepository;
import com.graduacionunal.backend.repositories.PlanEstudioRepository;
import com.graduacionunal.backend.repositories.PrerequisitoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// Implementacion para la interfaz PlanEstudioService

@Service
public class PlanEstudioServiceImpl implements PlanEstudioService {

    private final PlanEstudioRepository planEstudioRepository;
    private final MateriaRepository materiaRepository;
    private final PrerequisitoRepository prerequisitoRepository;
    private final MateriaPorPlanRepository materiaPorPlanRepository;
    private final KahnSemesterCalculator semesterCalculator = new KahnSemesterCalculator();

    public PlanEstudioServiceImpl(PlanEstudioRepository planEstudioRepository,
                                  MateriaRepository materiaRepository,
                                  PrerequisitoRepository prerequisitoRepository,
                                  MateriaPorPlanRepository materiaPorPlanRepository) {
        this.planEstudioRepository = planEstudioRepository;
        this.materiaRepository = materiaRepository;
        this.prerequisitoRepository = prerequisitoRepository;
        this.materiaPorPlanRepository = materiaPorPlanRepository;
    }

    @Override
    public List<PlanEstudio> obtenerTodos() {
        return planEstudioRepository.findAll();
    }

    @Override
    public PlanEstudio guardar(PlanEstudio planEstudio) {
        return planEstudioRepository.save(planEstudio);
    }

    @Override
    public List<PlanCreditosDTO> obtenerCreditosTotalesPorPlan() {
        return planEstudioRepository.obtenerCreditosTotalesPorPlan();
    }

    @Override
    public PlanEstudio obtenerPorId(Integer idPlanEstudio){
        return planEstudioRepository.findById(idPlanEstudio).orElse(null);
    }

    @Override
    public PlanEstudio eliminarPorId(Integer idPlanEstudio) {
        PlanEstudio planEstudio = planEstudioRepository.findById(idPlanEstudio).orElse(null);
        if (planEstudio != null) {
            planEstudioRepository.delete(planEstudio);
        }
        return planEstudio;
    }

    @Override
    public List<MateriaDTO> obtenerMateriasDePlanEstudio(Integer idPlanEstudio) {
        return planEstudioRepository.obtenerMateriasDePlanEstudio(idPlanEstudio);
    }

    @Override
    @Transactional(readOnly = true)
    public KahnSemesterCalculator.SemesterPlan calcularSemestres(Integer idPlanEstudio, int maxMateriasPorSemestre) {
        List<Materia> materias = materiaRepository.findByPlan(idPlanEstudio);
        List<Prerequisito> prerequisitos = prerequisitoRepository.findWithinPlan(idPlanEstudio);
        return semesterCalculator.calcularSemestresMinimos(materias, prerequisitos, maxMateriasPorSemestre);
    }

    @Override
    @Transactional
    public MateriaPlanDTO asignarMateriaAPlan(Integer idPlanEstudio, Integer idMateria) {
        PlanEstudio plan = planEstudioRepository.findById(idPlanEstudio)
                .orElseThrow(() -> new ResourceNotFoundException("Plan de estudio no encontrado con id " + idPlanEstudio));
        Materia materia = materiaRepository.findById(idMateria)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id " + idMateria));

        MateriaPorPlanId id = new MateriaPorPlanId(idPlanEstudio, idMateria);
        if (materiaPorPlanRepository.existsById(id)) {
            throw new IllegalArgumentException("La materia ya esta asignada al plan.");
        }
        MateriaPorPlan asignacion = new MateriaPorPlan(plan, materia);
        materiaPorPlanRepository.save(asignacion);
        return new MateriaPlanDTO(materia.getIdMateria(), materia.getNomMateria(), materia.getNumCreditos());
    }

    @Override
    @Transactional
    public void desasignarMateriaDePlan(Integer idPlanEstudio, Integer idMateria) {
        MateriaPorPlanId id = new MateriaPorPlanId(idPlanEstudio, idMateria);
        if (!materiaPorPlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("La materia no esta asignada al plan.");
        }
        materiaPorPlanRepository.deleteById(id);
    }
}
