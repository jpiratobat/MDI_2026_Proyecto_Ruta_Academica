package com.graduacionunal.backend.dto;

import java.util.List;

public class PlanEstudioDetalleResponse {
    private final Integer idPlan;
    private final String nomPlan;
    private final List<MateriaDTO> materias;

    public PlanEstudioDetalleResponse(Integer idPlan, String nomPlan, List<MateriaDTO> materias) {
        this.idPlan = idPlan;
        this.nomPlan = nomPlan;
        this.materias = materias;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public String getNomPlan() {
        return nomPlan;
    }

    public List<MateriaDTO> getMaterias() {
        return materias;
    }
}
