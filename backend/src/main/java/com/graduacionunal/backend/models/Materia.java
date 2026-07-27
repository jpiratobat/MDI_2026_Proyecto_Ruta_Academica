package com.graduacionunal.backend.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Materia") //Nombre de la tabla en la base de datos
public class Materia {

    //Campos de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMateria", nullable = false)
    private Integer idMateria;

    @Column(name = "nomMateria", length = 100, nullable = false)
    private String nomMateria;

    @Column(name = "numCreditos", nullable = false)
    private Integer numCreditos;

    // relación con MateriaPorPlan
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MateriaPorPlan> planes = new HashSet<>();

    // relaciones de prerequisitos:
    // materias que esta materia tiene como prerequisito (i.e. este es prerequisito de otras)
    @OneToMany(mappedBy = "prerequisito", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Prerequisito> esPrerequisitoDe = new HashSet<>();

    // materias que son prerequisitos para esta materia (i.e. esta tiene prerequisitos)
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Prerequisito> prerequisitos = new HashSet<>();

    public Materia() {}
    public Materia(String nomMateria, Integer numCreditos) {
        this.nomMateria = nomMateria;
        this.numCreditos = numCreditos;
    }

    // getters y setters
    public Integer getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }

    public String getNomMateria() {
        return nomMateria;
    }

    public void setNomMateria(String nomMateria) {
        this.nomMateria = nomMateria;
    }

    public Integer getNumCreditos() {
        return numCreditos;
    }

    public void setNumCreditos(Integer numCreditos) {
        this.numCreditos = numCreditos;
    }

    public Set<MateriaPorPlan> getPlanes() {
        return planes;
    }

    public void setPlanes(Set<MateriaPorPlan> planes) {
        this.planes = planes;
    }

    public Set<Prerequisito> getEsPrerequisitoDe() {
        return esPrerequisitoDe;
    }

    public void setEsPrerequisitoDe(Set<Prerequisito> esPrerequisitoDe) {
        this.esPrerequisitoDe = esPrerequisitoDe;
    }

    public Set<Prerequisito> getPrerequisitos() {
        return prerequisitos;
    }

    public void setPrerequisitos(Set<Prerequisito> prerequisitos) {
        this.prerequisitos = prerequisitos;
    }
}
