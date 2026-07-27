package com.graduacionunal.backend.services;

import java.util.List;
import com.graduacionunal.backend.models.Materia;


public interface MateriaService {
    List<Materia> obtenerTodos();
    Materia guardar (Materia materia);
    Materia obtenerPorId(Integer idMateria);
    Materia eliminarPorId(Integer idMateria);
}

