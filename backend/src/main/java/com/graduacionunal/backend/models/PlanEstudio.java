package com.graduacionunal.backend.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PlanEstudio") //Nombre de la tabla en la base de datos
public class PlanEstudio {

    //Campos de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPlan", nullable = false)
    private Integer idPlan;

    @Column(name = "nomPlan", length = 100, nullable = false)
    private String nomPlan;

    // Relación uno-a-muchos con MateriaPorPlan
    @OneToMany(mappedBy = "planEstudio", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MateriaPorPlan> materiasPorPlan = new HashSet<>();

    //Construtores
    public PlanEstudio() {
    }

    public PlanEstudio(String nomPlan) {
        this.nomPlan = nomPlan;
    }

    // getters y setters
    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public String getNomPlan() {
        return nomPlan;
    }

    public void setNomPlan(String nomPlan) {
        this.nomPlan = nomPlan;
    }

    public Set<MateriaPorPlan> getMateriasPorPlan() {
        return materiasPorPlan;
    }

    public void setMateriasPorPlan(Set<MateriaPorPlan> materiasPorPlan) {
        this.materiasPorPlan = materiasPorPlan;
    }
}
