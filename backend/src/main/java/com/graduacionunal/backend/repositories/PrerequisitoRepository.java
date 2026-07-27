package com.graduacionunal.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.graduacionunal.backend.models.Prerequisito;
import com.graduacionunal.backend.models.PrerequisitoId;

public interface PrerequisitoRepository extends JpaRepository<Prerequisito, PrerequisitoId> {

    /*
     * Prerequisitos de materias que pertenecen a un plan de estudio dado.
     * Incluye prerequisitos aunque la materia prerequisito no esté dentro del mismo plan.
     */
    @Query("""
        SELECT DISTINCT p
        FROM Prerequisito p
        JOIN FETCH p.materia m
        JOIN FETCH p.prerequisito prereq
        JOIN m.planes mp
        WHERE mp.planEstudio.idPlan = :idPlan
        """)
    List<Prerequisito> findByPlan(@Param("idPlan") Integer idPlan);

    /*
     * Prerequisitos donde tanto la materia como el prerequisito están en el mismo plan.
     */
    @Query("""
        SELECT DISTINCT p
        FROM Prerequisito p
        JOIN FETCH p.materia m
        JOIN FETCH p.prerequisito prereq
        JOIN m.planes mp
        JOIN prereq.planes prereqMp
        WHERE mp.planEstudio.idPlan = :idPlan
          AND prereqMp.planEstudio.idPlan = :idPlan
        """)
    List<Prerequisito> findWithinPlan(@Param("idPlan") Integer idPlan);

    @Query("""
        SELECT p
        FROM Prerequisito p
        JOIN FETCH p.materia m
        JOIN FETCH p.prerequisito prereq
        WHERE m.idMateria = :idMateria
        """)
    List<Prerequisito> findIncomingByMateria(@Param("idMateria") Integer idMateria);

    @Query("""
        SELECT p
        FROM Prerequisito p
        JOIN FETCH p.materia m
        JOIN FETCH p.prerequisito prereq
        WHERE prereq.idMateria = :idMateria
        """)
    List<Prerequisito> findOutgoingByMateria(@Param("idMateria") Integer idMateria);
}
