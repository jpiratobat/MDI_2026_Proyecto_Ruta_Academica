package com.graduacionunal.backend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.graduacionunal.backend.datastructures.KahnSemesterCalculator;
import com.graduacionunal.backend.datastructures.LinkedList;
import com.graduacionunal.backend.dto.MateriaDTO;
import com.graduacionunal.backend.dto.MateriaPlanDTO;
import com.graduacionunal.backend.dto.PlanCreditosDTO;
import com.graduacionunal.backend.dto.PlanEstudioDetalleResponse;
import com.graduacionunal.backend.dto.PlanEstudioResponse;
import com.graduacionunal.backend.dto.SemesterPlanResponse;
import com.graduacionunal.backend.exceptions.ResourceNotFoundException;
import com.graduacionunal.backend.models.Materia;
import com.graduacionunal.backend.models.PlanEstudio;
import com.graduacionunal.backend.services.PlanEstudioService;

@RestController
@RequestMapping("/api/planes")
public class PlanEstudioController {

    private final PlanEstudioService planEstudioService;

    public PlanEstudioController(PlanEstudioService planEstudioService) {
        this.planEstudioService = planEstudioService;
    }

    @GetMapping
    public List<PlanEstudioResponse> obtenerPlanes() {
        return planEstudioService.obtenerTodos().stream()
                .map(plan -> new PlanEstudioResponse(plan.getIdPlan(), plan.getNomPlan()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PlanEstudioDetalleResponse obtenerPlanPorId(@PathVariable Integer id) {
        PlanEstudio plan = requirePlan(id);
        List<MateriaDTO> materias = planEstudioService.obtenerMateriasDePlanEstudio(id);
        return new PlanEstudioDetalleResponse(plan.getIdPlan(), plan.getNomPlan(), materias);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanEstudioResponse crearPlan(@RequestBody PlanEstudio planEstudio) {
        PlanEstudio creado = planEstudioService.guardar(planEstudio);
        return new PlanEstudioResponse(creado.getIdPlan(), creado.getNomPlan());
    }

    @DeleteMapping("/{id}")
    public PlanEstudioResponse eliminarPlan(@PathVariable Integer id) {
        PlanEstudio eliminado = planEstudioService.eliminarPorId(id);
        if (eliminado == null) {
            throw new ResourceNotFoundException("Plan de estudio no encontrado con id " + id);
        }
        return new PlanEstudioResponse(eliminado.getIdPlan(), eliminado.getNomPlan());
    }

    @GetMapping("/{id}/materias")
    public List<MateriaDTO> materiasDePlan(@PathVariable Integer id) {
        requirePlan(id);
        return planEstudioService.obtenerMateriasDePlanEstudio(id);
    }

    @PostMapping("/{id}/materias/{idMateria}")
    @ResponseStatus(HttpStatus.CREATED)
    public MateriaPlanDTO asignarMateria(@PathVariable Integer id, @PathVariable Integer idMateria) {
        return planEstudioService.asignarMateriaAPlan(id, idMateria);
    }

    @DeleteMapping("/{id}/materias/{idMateria}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desasignarMateria(@PathVariable Integer id, @PathVariable Integer idMateria) {
        planEstudioService.desasignarMateriaDePlan(id, idMateria);
    }

    @GetMapping("/creditos")
    public List<PlanCreditosDTO> creditosTotales() {
        return planEstudioService.obtenerCreditosTotalesPorPlan();
    }

    @GetMapping("/{id}/semestres")
    public ResponseEntity<SemesterPlanResponse> calcularSemestres(@PathVariable Integer id,
            @RequestParam(name = "max", defaultValue = "5") int maxMateriasPorSemestre) {
        requirePlan(id);
        KahnSemesterCalculator.SemesterPlan resultado = planEstudioService.calcularSemestres(id, maxMateriasPorSemestre);
        SemesterPlanResponse response = mapSemesterPlan(resultado);
        if (resultado.hasCycle()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    private PlanEstudio requirePlan(Integer idPlan) {
        PlanEstudio plan = planEstudioService.obtenerPorId(idPlan);
        if (plan == null) {
            throw new ResourceNotFoundException("Plan de estudio no encontrado con id " + idPlan);
        }
        return plan;
    }

    private SemesterPlanResponse mapSemesterPlan(KahnSemesterCalculator.SemesterPlan semesterPlan) {
        List<SemesterPlanResponse.SemesterResponse> semestres = new ArrayList<>();
        int numero = 1;
        for (LinkedList<Materia> semestre : semesterPlan.getSemesters()) {
            List<MateriaPlanDTO> materias = new ArrayList<>();
            for (Materia materia : semestre) {
                materias.add(new MateriaPlanDTO(materia.getIdMateria(), materia.getNomMateria(), materia.getNumCreditos()));
            }
            semestres.add(new SemesterPlanResponse.SemesterResponse(numero++, materias));
        }
        return new SemesterPlanResponse(semesterPlan.getMinSemesters(), semesterPlan.hasCycle(), semestres);
    }
}
