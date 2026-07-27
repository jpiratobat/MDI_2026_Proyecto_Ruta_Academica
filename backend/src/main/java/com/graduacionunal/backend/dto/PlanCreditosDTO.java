package com.graduacionunal.backend.dto;

public class PlanCreditosDTO {
    private String nomPlan;
    // SUM in JPQL returns a Long, so we store the aggregate as Long to match the projection
    private Long totalCreditos;

    public PlanCreditosDTO(String nomPlan, Long totalCreditos) {
        this.nomPlan = nomPlan;
        this.totalCreditos = totalCreditos;
    }

    public String getNomPlan() {
        return nomPlan;
    }

    public Long getTotalCreditos() {
        return totalCreditos;
    }
}
