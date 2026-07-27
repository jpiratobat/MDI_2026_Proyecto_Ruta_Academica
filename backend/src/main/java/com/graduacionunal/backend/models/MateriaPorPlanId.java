package com.graduacionunal.backend.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MateriaPorPlanId implements Serializable {

    @Column(name = "idPlan", nullable = false)
    private Integer idPlan;

    @Column(name = "idMateria", nullable = false)
    private Integer idMateria;

    public MateriaPorPlanId() {
    }

    public MateriaPorPlanId(Integer idPlan, Integer idMateria) {
        this.idPlan = idPlan;
        this.idMateria = idMateria;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public Integer getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MateriaPorPlanId that = (MateriaPorPlanId) o;
        return Objects.equals(idPlan, that.idPlan) &&
               Objects.equals(idMateria, that.idMateria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlan, idMateria);
    }
}
