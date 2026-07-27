package com.graduacionunal.backend.dto;

public class MateriaDTO {
    private Integer idMateria;
    private String nomMateria;
    private Integer numCreditos;

    public MateriaDTO(Integer idMateria, String nomMateria, Integer numCreditos) {
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
