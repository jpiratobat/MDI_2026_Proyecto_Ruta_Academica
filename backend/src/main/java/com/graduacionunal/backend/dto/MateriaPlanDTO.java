package com.graduacionunal.backend.dto;

public class MateriaPlanDTO {
    private final Integer idMateria;
    private final String nomMateria;
    private final Integer numCreditos;

    public MateriaPlanDTO(Integer idMateria, String nomMateria, Integer numCreditos) {
        this.idMateria = idMateria;
        this.nomMateria = nomMateria;
        this.numCreditos = numCreditos;
    }

    public Integer getIdMateria() {
        return idMateria;
    }

    public String getNomMateria() {
        return nomMateria;
    }

    public Integer getNumCreditos() {
        return numCreditos;
    }
}
