package com.graduacionunal.backend.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PrerequisitoId implements Serializable {

    @Column(name = "idMateria", nullable = false)
    private Integer idMateria;

    @Column(name = "idPrerequisito", nullable = false)
    private Integer idPrerequisito;

    //Construtores
    public PrerequisitoId() {
    }

    public PrerequisitoId(Integer idMateria, Integer idPrerequisito) {
        this.idMateria = idMateria;
        this.idPrerequisito = idPrerequisito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrerequisitoId)) return false;
        PrerequisitoId that = (PrerequisitoId) o;
        return Objects.equals(idMateria, that.idMateria) && Objects.equals(idPrerequisito, that.idPrerequisito);
    }

    @Override
    public int hashCode() { 
        return Objects.hash(idMateria, idPrerequisito); 
    }

    // getters/setters
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
