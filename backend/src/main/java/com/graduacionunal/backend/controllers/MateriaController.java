package com.graduacionunal.backend.controllers;

import com.graduacionunal.backend.models.Materia;
import com.graduacionunal.backend.services.MateriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/materias")
public class MateriaController {
    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService){
        this.materiaService = materiaService;
    }

    @GetMapping
    public List<Materia> obtenerTodasLasMaterias() {
        return materiaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Materia obtenerMateriaPorId(@PathVariable Integer id) {
        return materiaService.obtenerPorId(id);
    }

    @PostMapping
    public Materia crearMateria(@RequestBody Materia materia) {
        return materiaService.guardar(materia);
    }

    @DeleteMapping("/{id}")
    public Materia eliminarMateria(@PathVariable Integer id) {
        return materiaService.eliminarPorId(id);
    }

}
