package com.graduacionunal.backend.dto;

public class PrerequisitoDTO {
    private final Integer idMateria;
    private final String nomMateria;
    private final Integer idPrerequisito;
    private final String nomPrerequisito;

    public PrerequisitoDTO(Integer idMateria, String nomMateria, Integer idPrerequisito, String nomPrerequisito) {
        this.idMateria = idMateria;
        this.nomMateria = nomMateria;
        this.idPrerequisito = idPrerequisito;
        this.nomPrerequisito = nomPrerequisito;
    }

    public Integer getIdMateria() {
        return idMateria;
    }

    public String getNomMateria() {
        return nomMateria;
    }

    public Integer getIdPrerequisito() {
        return idPrerequisito;
    }

    public String getNomPrerequisito() {
        return nomPrerequisito;
    }
}
