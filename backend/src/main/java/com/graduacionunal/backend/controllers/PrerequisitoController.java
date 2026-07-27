package com.graduacionunal.backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.graduacionunal.backend.dto.PrerequisitoDTO;
import com.graduacionunal.backend.dto.PrerequisitoRequest;
import com.graduacionunal.backend.exceptions.ResourceNotFoundException;
import com.graduacionunal.backend.models.Materia;
import com.graduacionunal.backend.models.Prerequisito;
import com.graduacionunal.backend.services.PlanEstudioService;
import com.graduacionunal.backend.services.PrerequisitoService;

@RestController
@RequestMapping("/api/prerequisitos")
public class PrerequisitoController {

    private final PrerequisitoService prerequisitoService;
    private final PlanEstudioService planEstudioService;

    public PrerequisitoController(PrerequisitoService prerequisitoService,
                                  PlanEstudioService planEstudioService) {
        this.prerequisitoService = prerequisitoService;
        this.planEstudioService = planEstudioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrerequisitoDTO crearPrerequisito(@RequestBody PrerequisitoRequest request) {
        Prerequisito prerequisito = prerequisitoService.crear(request.getIdMateria(), request.getIdPrerequisito());
        return toDto(prerequisito);
    }

    @GetMapping("/plan/{planId}")
    public List<PrerequisitoDTO> listarPorPlan(@PathVariable Integer planId) {
        validarPlan(planId);
        return prerequisitoService.listarPorPlan(planId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/materia/{idMateria}/entrantes")
    public List<PrerequisitoDTO> prerequisitosEntrantes(@PathVariable Integer idMateria) {
        return prerequisitoService.listarEntrantesPorMateria(idMateria).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/materia/{idMateria}/salientes")
    public List<PrerequisitoDTO> prerequisitosSalientes(@PathVariable Integer idMateria) {
        return prerequisitoService.listarSalientesPorMateria(idMateria).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{idMateria}/{idPrerequisito}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPrerequisito(@PathVariable Integer idMateria, @PathVariable Integer idPrerequisito) {
        prerequisitoService.eliminar(idMateria, idPrerequisito);
    }

    private void validarPlan(Integer idPlan) {
        if (planEstudioService.obtenerPorId(idPlan) == null) {
            throw new ResourceNotFoundException("Plan de estudio no encontrado con id " + idPlan);
        }
    }

    private PrerequisitoDTO toDto(Prerequisito prerequisito) {
        Materia materia = prerequisito.getMateria();
        Materia prereq = prerequisito.getPrerequisito();
        Integer idMateria = materia != null ? materia.getIdMateria() : null;
        Integer idPrereq = prereq != null ? prereq.getIdMateria() : null;
        String nomMateria = materia != null ? materia.getNomMateria() : null;
        String nomPrereq = prereq != null ? prereq.getNomMateria() : null;
        return new PrerequisitoDTO(idMateria, nomMateria, idPrereq, nomPrereq);
    }
}
