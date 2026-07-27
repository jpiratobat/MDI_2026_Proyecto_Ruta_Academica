package com.graduacionunal.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.graduacionunal.backend.models.Materia;
import com.graduacionunal.backend.repositories.MateriaRepository;

//Clase que implementa la interfaz MateriaService
@Service
public class MateriaServiceImpl implements MateriaService {

    private final MateriaRepository materiaRepository;


    public MateriaServiceImpl(MateriaRepository materiaRepository){
        this.materiaRepository = materiaRepository;
    }

    @Override
    public List<Materia> obtenerTodos() { 
        return materiaRepository.findAll();
    }

    @Override
    public Materia guardar(Materia materia) {
        return materiaRepository.save(materia);
    }

    @Override
    public Materia obtenerPorId(Integer idMateria){
        return materiaRepository.findById(idMateria).orElse(null);
    }

    @Override
    public Materia eliminarPorId(Integer idMateria){
        Materia materia = materiaRepository.findById(idMateria).orElse(null);

        if(materia != null){
            materiaRepository.delete(materia);
        }

        return materia;

    }
}
