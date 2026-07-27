package com.graduacionunal.backend.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.graduacionunal.backend.datastructures.queue.MyQueue;
import com.graduacionunal.backend.datastructures.queue.MyQueueArrayList;
import com.graduacionunal.backend.datastructures.queue.MyQueueUnderFlowException;
import com.graduacionunal.backend.models.Materia;
import com.graduacionunal.backend.models.Prerequisito;

/**
 * Aplica el algoritmo de Kahn (ordenamiento topologico + BFS) para calcular
 * la cantidad minima de semestres que necesita un estudiante para completar
 * todas las materias, respetando un limite de materias por semestre.
 *
 * El algoritmo usa las estructuras de datos implementadas en el proyecto
 * (Graph, LinkedList y MyQueue) para mantener la lista de adyacencia y la
 * cola de nodos sin prerequisitos pendientes.
 */
public class KahnSemesterCalculator {

    /**
     * Resultado de la simulacion de semestres.
     */
    public static class SemesterPlan {
        private final int minSemesters;
        private final LinkedList<LinkedList<Materia>> semesters;
        private final boolean hasCycle;

        public SemesterPlan(int minSemesters, LinkedList<LinkedList<Materia>> semesters, boolean hasCycle) {
            this.minSemesters = minSemesters;
            this.semesters = semesters;
            this.hasCycle = hasCycle;
        }

        public int getMinSemesters() {
            return minSemesters;
        }

        public LinkedList<LinkedList<Materia>> getSemesters() {
            return semesters;
        }

        public boolean hasCycle() {
            return hasCycle;
        }
    }

    /**
     * Calcula el numero minimo de semestres que se necesitan para cursar todas
     * las materias respetando el limite de materias por semestre. Si existe un
     * ciclo en los prerequisitos, {@code hasCycle} sera verdadero y
     * {@code minSemesters} tomara el valor -1.
     *
     * @param materias               lista de materias del plan.
     * @param prerequisitos          relaciones de prerequisito (idPrerequisito -> idMateria).
     * @param maxMateriasPorSemestre limite de materias que se pueden cursar en un semestre.
     * @return un {@link SemesterPlan} con la cantidad minima de semestres y el detalle por semestre.
     */
    public SemesterPlan calcularSemestresMinimos(List<Materia> materias, List<Prerequisito> prerequisitos,
                                                 int maxMateriasPorSemestre) {
        if (materias == null || prerequisitos == null) {
            throw new IllegalArgumentException("Las listas de materias y prerequisitos no pueden ser nulas.");
        }
        if (maxMateriasPorSemestre <= 0) {
            throw new IllegalArgumentException("El maximo de materias por semestre debe ser mayor que cero.");
        }
        if (materias.isEmpty()) {
            return new SemesterPlan(0, new LinkedList<>(), false);
        }

        int n = materias.size();

        // Mapeo idMateria -> indice para manejar arreglos compactos.
        Map<Integer, Integer> indicePorId = new HashMap<>(n * 2);
        Materia[] materiasPorIndice = new Materia[n];
        for (int i = 0; i < n; i++) {
            Materia materia = materias.get(i);
            materiasPorIndice[i] = materia;
            indicePorId.put(materia.getIdMateria(), i);
        }

        // Lista de adyacencia usando el grafo custom (dirigido).
        Graph grafo = new Graph(n, true);
        int[] indegree = new int[n];
        List<String> referenciasInvalidas = new ArrayList<>();

        for (Prerequisito prerequisito : prerequisitos) {
            Materia prereqMateria = prerequisito.getPrerequisito();
            Materia materiaObjetivo = prerequisito.getMateria();

            Integer prereqId = prereqMateria != null ? prereqMateria.getIdMateria() : null;
            Integer materiaId = materiaObjetivo != null ? materiaObjetivo.getIdMateria() : null;

            Integer prereqIndex = prereqId != null ? indicePorId.get(prereqId) : null;
            Integer materiaIndex = materiaId != null ? indicePorId.get(materiaId) : null;

            // Si la relacion hace referencia a una materia fuera de la lista, la registramos para reportar el error.
            if (prereqIndex == null || materiaIndex == null) {
                StringBuilder detalle = new StringBuilder("prerequisito{materia=")
                        .append(materiaId)
                        .append(", prerequisito=")
                        .append(prereqId)
                        .append("} faltante: ");
                if (prereqIndex == null) {
                    detalle.append("prerequisito");
                    if (materiaIndex == null) {
                        detalle.append(" y materia");
                    }
                } else if (materiaIndex == null) {
                    detalle.append("materia");
                }
                referenciasInvalidas.add(detalle.toString());
                continue;
            }

            grafo.addEdge(prereqIndex, materiaIndex);
            indegree[materiaIndex] += 1;
        }

        if (!referenciasInvalidas.isEmpty()) {
            throw new InvalidPrerequisiteReferenceException(referenciasInvalidas);
        }

        MyQueue<Integer> cola = new MyQueueArrayList<>(Math.max(1, n));
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                cola.enqueue(i);
            }
        }

        LinkedList<LinkedList<Materia>> semestres = new LinkedList<>();
        int procesadas = 0;

        // Procesamos por niveles (semestral): los nodos con indegree cero al inicio
        // del semestre son los que pueden cursarse en ese semestre.
        while (!cola.isEmpty()) {
            int disponiblesEsteSemestre = cola.size();
            int aTomar = Math.min(maxMateriasPorSemestre, disponiblesEsteSemestre);
            if (aTomar == 0) {
                break;
            }

            LinkedList<Materia> semestreActual = new LinkedList<>();
            for (int i = 0; i < aTomar; i++) {
                int materiaIndex = safeDequeue(cola);
                procesadas += 1;
                semestreActual.pushBack(materiasPorIndice[materiaIndex]);

                for (Integer dependiente : grafo.getNeighbors(materiaIndex)) {
                    indegree[dependiente] -= 1;
                    if (indegree[dependiente] == 0) {
                        cola.enqueue(dependiente);
                    }
                }
            }

            semestres.pushBack(semestreActual);
        }

        boolean tieneCiclo = procesadas < n;
        int minSemestres = tieneCiclo ? -1 : semestres.size();
        return new SemesterPlan(minSemestres, semestres, tieneCiclo);
    }

    // Extraemos de la cola controlando la excepcion checked de la implementacion.
    private int safeDequeue(MyQueue<Integer> cola) {
        try {
            Integer value = cola.dequeue();
            if (value == null) {
                throw new IllegalStateException("Se obtuvo un valor nulo desde la cola.");
            }
            return value;
        } catch (MyQueueUnderFlowException e) {
            throw new IllegalStateException("La cola quedo vacia de forma inesperada durante el algoritmo.", e);
        }
    }
}
