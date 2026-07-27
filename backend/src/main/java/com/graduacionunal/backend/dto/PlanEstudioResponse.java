package com.graduacionunal.backend.dto;

public class PlanEstudioResponse {
    private final Integer idPlan;
    private final String nomPlan;

    public PlanEstudioResponse(Integer idPlan, String nomPlan) {
        this.idPlan = idPlan;
        this.nomPlan = nomPlan;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public String getNomPlan() {
        return nomPlan;
    }
}
