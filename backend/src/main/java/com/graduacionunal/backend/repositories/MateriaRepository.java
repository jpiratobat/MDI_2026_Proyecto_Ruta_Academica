package com.graduacionunal.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduacionunal.backend.models.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {

    // Materias asociadas a un plan de estudio específico
    @Query("""
        SELECT m FROM Materia m
        JOIN m.planes mp
        WHERE mp.planEstudio.idPlan = :idPlan
        """)
    List<Materia> findByPlan(@Param("idPlan") Integer idPlan);

    // Materias con sus prerequisitos (para optimizar lecturas)
    @EntityGraph(attributePaths = { "prerequisitos", "esPrerequisitoDe" })
    @Query("SELECT m FROM Materia m WHERE m.idMateria IN :ids")
    List<Materia> findWithPrereqs(@Param("ids") List<Integer> ids);
}
