package com.graduacionunal.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduacionunal.backend.exceptions.ResourceNotFoundException;
import com.graduacionunal.backend.models.Materia;
import com.graduacionunal.backend.models.Prerequisito;
import com.graduacionunal.backend.models.PrerequisitoId;
import com.graduacionunal.backend.repositories.MateriaRepository;
import com.graduacionunal.backend.repositories.PrerequisitoRepository;

@Service
public class PrerequisitoServiceImpl implements PrerequisitoService {

    private final PrerequisitoRepository prerequisitoRepository;
    private final MateriaRepository materiaRepository;

    public PrerequisitoServiceImpl(PrerequisitoRepository prerequisitoRepository,
                                   MateriaRepository materiaRepository) {
        this.prerequisitoRepository = prerequisitoRepository;
        this.materiaRepository = materiaRepository;
    }

    @Override
    @Transactional
    public Prerequisito crear(Integer idMateria, Integer idPrerequisito) {
        if (idMateria == null || idPrerequisito == null) {
            throw new IllegalArgumentException("Los ids de materia y prerequisito son obligatorios.");
        }
        if (idMateria.equals(idPrerequisito)) {
            throw new IllegalArgumentException("Una materia no puede ser prerequisito de si misma.");
        }

        Materia materia = materiaRepository.findById(idMateria)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id " + idMateria));
        Materia prerequisito = materiaRepository.findById(idPrerequisito)
                .orElseThrow(() -> new ResourceNotFoundException("Materia prerequisito no encontrada con id " + idPrerequisito));

        PrerequisitoId id = new PrerequisitoId(idMateria, idPrerequisito);
        if (prerequisitoRepository.existsById(id)) {
            throw new IllegalArgumentException("El prerequisito ya existe para la materia.");
        }

        Prerequisito nuevoPrerequisito = new Prerequisito(materia, prerequisito);
        return prerequisitoRepository.save(nuevoPrerequisito);
    }

    @Override
    @Transactional
    public void eliminar(Integer idMateria, Integer idPrerequisito) {
        PrerequisitoId id = new PrerequisitoId(idMateria, idPrerequisito);
        if (!prerequisitoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontro el prerequisito solicitado.");
        }
        prerequisitoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prerequisito> listarPorPlan(Integer idPlan) {
        return prerequisitoRepository.findByPlan(idPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prerequisito> listarEntrantesPorMateria(Integer idMateria) {
        validarMateriaExiste(idMateria);
        return prerequisitoRepository.findIncomingByMateria(idMateria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prerequisito> listarSalientesPorMateria(Integer idMateria) {
        validarMateriaExiste(idMateria);
        return prerequisitoRepository.findOutgoingByMateria(idMateria);
    }

    private void validarMateriaExiste(Integer idMateria) {
        if (!materiaRepository.existsById(idMateria)) {
            throw new ResourceNotFoundException("Materia no encontrada con id " + idMateria);
        }
    }
}
