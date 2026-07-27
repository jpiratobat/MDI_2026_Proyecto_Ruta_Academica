package com.graduacionunal.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "MateriaPorPlan") //Nombre de la tabla en la base de datos
public class MateriaPorPlan {

    @EmbeddedId
    private MateriaPorPlanId id = new MateriaPorPlanId();

    // dueño de la relación hacia PlanEstudio
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPlan") // usa la columna idPlan del EmbeddedId
    @JoinColumn(name = "idPlan", nullable = false)
    private PlanEstudio planEstudio; 

    // dueño de la relación hacia Materia
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMateria") // usa la columna idMateria del EmbeddedId
    @JoinColumn(name = "idMateria", nullable = false)
    private Materia materia;
    
    //Construtores
    public MateriaPorPlan() {
    }

    public MateriaPorPlan(PlanEstudio planEstudio, Materia materia) {
        this.planEstudio = planEstudio;
        this.materia = materia;
        this.id = new MateriaPorPlanId(planEstudio.getIdPlan(), materia.getIdMateria());
    }

    // getters y setters
    public MateriaPorPlanId getId() { 
        return id; 
    }

    public void setId(MateriaPorPlanId id) { 
        this.id = id; 
    }

    public PlanEstudio getPlanEstudio() { 
        return planEstudio; 
    }

    public void setPlanEstudio(PlanEstudio planEstudio) { 
        this.planEstudio = planEstudio; 
    }

    public Materia getMateria() { 
        return materia; 
    }

    public void setMateria(Materia materia) { 
        this.materia = materia; 
    }
}
