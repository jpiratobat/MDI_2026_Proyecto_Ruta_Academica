package com.graduacionunal.backend.dto;

public class PrerequisitoRequest {
    private Integer idMateria;
    private Integer idPrerequisito;

    public Integer getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }

    public Integer getIdPrerequisito() {
        return idPrerequisito;
    }

    public void setIdPrerequisito(Integer idPrerequisito) {
        this.idPrerequisito = idPrerequisito;
    }
}
