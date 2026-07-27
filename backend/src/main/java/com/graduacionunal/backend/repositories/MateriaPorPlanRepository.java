package com.graduacionunal.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduacionunal.backend.models.MateriaPorPlan;
import com.graduacionunal.backend.models.MateriaPorPlanId;

public interface MateriaPorPlanRepository extends JpaRepository<MateriaPorPlan, MateriaPorPlanId> {

    @Query("""
        SELECT mp FROM MateriaPorPlan mp
        WHERE mp.planEstudio.idPlan = :idPlan
        """)
    List<MateriaPorPlan> findByPlan(@Param("idPlan") Integer idPlan);

    @Query("""
        SELECT mp FROM MateriaPorPlan mp
        WHERE mp.materia.idMateria = :idMateria
        """)
    List<MateriaPorPlan> findByMateria(@Param("idMateria") Integer idMateria);
}
