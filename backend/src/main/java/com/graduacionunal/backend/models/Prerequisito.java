package com.graduacionunal.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Prerequisito") //Nombre de la tabla en la base de datos
public class Prerequisito {

    @EmbeddedId
    private PrerequisitoId id = new PrerequisitoId();

    // Materia que tiene el prerequisito (la materia objetivo)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMateria")
    @JoinColumn(name = "idMateria", nullable = false)
    private Materia materia;

    // Materia que actúa como prerequisito (la materia requerida)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPrerequisito")
    @JoinColumn(name = "idPrerequisito", nullable = false)
    private Materia prerequisito;

    //Construtores
    public Prerequisito() {
    }

    public Prerequisito(Materia materia, Materia prerequisito) {
        this.materia = materia;
        this.prerequisito = prerequisito;
        this.id = new PrerequisitoId(materia.getIdMateria(), prerequisito.getIdMateria());
    }

    // getters y setters
    public PrerequisitoId getId() { 
        return id; 
    }

    public void setId(PrerequisitoId id) { 
        this.id = id; 
    }

    public Materia getMateria() { 
        return materia; 
    }

    public void setMateria(Materia materia) { 
        this.materia = materia; 
    }

    public Materia getPrerequisito() { 
        return prerequisito; 
    }

    public void setPrerequisito(Materia prerequisito) { 
        this.prerequisito = prerequisito; 
    }
}
