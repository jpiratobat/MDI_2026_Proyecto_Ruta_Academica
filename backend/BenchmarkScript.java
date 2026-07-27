// Este archivo se puede colocar en cualquier paquete o sin paquete.
// Si decide incluir un paquete, asegúrese de ubicar el archivo en el
// directorio correspondiente. Para simplificar la compilación, se omite
// la declaración de paquete.

import com.graduacionunal.backend.datastructures.Graph;
import com.graduacionunal.backend.datastructures.LinkedList;
import com.graduacionunal.backend.datastructures.queue.MyQueue;
import com.graduacionunal.backend.datastructures.queue.MyQueueArrayList;
import com.graduacionunal.backend.datastructures.queue.MyQueueUnderFlowException;
import com.graduacionunal.backend.datastructures.KahnSemesterCalculator;
import com.graduacionunal.backend.models.Materia;
import com.graduacionunal.backend.models.Prerequisito;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * BenchmarkScript genera datos empíricos para evaluar el rendimiento de las
 * estructuras de datos y algoritmos implementados en el proyecto de
 * graduación. Este programa mide los tiempos de ejecución de operaciones
 * típicas sobre las estructuras {@link LinkedList}, {@link Graph},
 * {@link MyQueueArrayList} y del algoritmo de planificación de semestres
 * implementado en {@link KahnSemesterCalculator}. Los resultados se
 * escriben en un archivo CSV para facilitar su análisis posterior (por
 * ejemplo, mediante Python/Excel para graficar curvas de rendimiento).
 *
 * <p>Para ejecutar este script se recomienda compilar todo el módulo backend
 * mediante Maven y ejecutar la clase con el JDK 17 (por ejemplo,
 * <code>mvn -q -pl backend -am package</code> y luego
 * <code>java -cp target/backend-0.0.1-SNAPSHOT.jar \
 * com.graduacionunal.backend.benchmark.BenchmarkScript</code>).</p>
 */
public class BenchmarkScript {

    /**
     * Registra un resultado de benchmark. Cada entrada contiene el nombre de
     * la estructura o algoritmo probado, la operación evaluada, el tamaño de
     * entrada y el tiempo transcurrido en milisegundos.
     */
    private static class Result {
        final String structure;
        final String operation;
        final int size;
        final double timeMs;

        Result(String structure, String operation, int size, double timeMs) {
            this.structure = structure;
            this.operation = operation;
            this.size = size;
            this.timeMs = timeMs;
        }
    }

    public static void main(String[] args) {
        // Configuración de tamaños de entrada. Ajustar según recursos disponibles.
        int[] sizes = new int[]{10_000, 50_000, 100_000};
        List<Result> results = new ArrayList<>();

        for (int n : sizes) {
            // Benchmark de LinkedList
            results.addAll(benchmarkLinkedList(n));
            // Benchmark de Graph
            results.addAll(benchmarkGraph(n));
            // Benchmark de MyQueueArrayList
            results.addAll(benchmarkQueue(n));
            // Benchmark de KahnSemesterCalculator
            results.add(benchmarkSemesterCalculator(n));
        }

        // Escribir resultados a CSV
        writeResultsCSV(results, "benchmark_results.csv");
        // Imprimir resumen en consola
        for (Result r : results) {
            System.out.printf("%s,%s,%d,%.3f ms%n", r.structure, r.operation, r.size, r.timeMs);
        }
    }

    /**
     * Ejecuta pruebas sobre la estructura de lista enlazada y devuelve
     * resultados. Se miden operaciones pushBack (inserción al final),
     * contains (búsqueda lineal) y popFront (extracción desde el inicio).
     *
     * @param size número de elementos de prueba
     * @return lista de resultados
     */
    private static List<Result> benchmarkLinkedList(int size) {
        List<Result> results = new ArrayList<>();
        LinkedList<Integer> list = new LinkedList<>();
        long start, end;

        // Medir inserción al final (pushBack)
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            list.pushBack(i);
        }
        end = System.nanoTime();
        results.add(new Result("LinkedList", "pushBack", size, nanosToMillis(end - start)));

        // Medir búsqueda contains para el último elemento
        start = System.nanoTime();
        boolean contains = list.contains(size - 1);
        end = System.nanoTime();
        // Uso de la variable contiene para evitar optimización
        if (!contains) {
            throw new IllegalStateException("contains() devolvió false para un elemento existente");
        }
        results.add(new Result("LinkedList", "contains", size, nanosToMillis(end - start)));

        // Medir extracción desde el inicio (popFront)
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            Integer val = list.popFront();
            if (val == null) {
                throw new IllegalStateException("popFront() devolvió null antes de tiempo");
            }
        }
        end = System.nanoTime();
        results.add(new Result("LinkedList", "popFront", size, nanosToMillis(end - start)));

        return results;
    }

    /**
     * Mide el rendimiento de operaciones básicas sobre el grafo: construcción
     * de aristas y recorridos DFS y BFS. Para evitar grafos muy densos que
     * saturen la memoria, cada nodo se conecta como mucho a un pequeño número
     * de sucesores determinísticos.
     *
     * @param size número de vértices del grafo
     * @return lista de resultados
     */
    private static List<Result> benchmarkGraph(int size) {
        List<Result> results = new ArrayList<>();
        Graph g = new Graph(size, true);
        long start, end;

        // Construcción de aristas: conectar cada vértice i con (i+1) % size
        start = System.nanoTime();
        for (int i = 0; i < size - 1; i++) {
            g.addEdge(i, i + 1);
        }
        end = System.nanoTime();
        results.add(new Result("Graph", "addEdge", size, nanosToMillis(end - start)));

        // Medir DFS (countNodesDFS)
        start = System.nanoTime();
        int countDFS = g.countNodesDFS();
        end = System.nanoTime();
        // Validar que el recorrido visitó todos los nodos
        if (countDFS != size) {
            throw new IllegalStateException("DFS visitó " + countDFS + " vértices de " + size);
        }
        results.add(new Result("Graph", "DFS", size, nanosToMillis(end - start)));

        // Medir BFS (countNodesBFS)
        start = System.nanoTime();
        int countBFS = g.countNodesBFS();
        end = System.nanoTime();
        if (countBFS != size) {
            throw new IllegalStateException("BFS visitó " + countBFS + " vértices de " + size);
        }
        results.add(new Result("Graph", "BFS", size, nanosToMillis(end - start)));

        return results;
    }

    /**
     * Ejecuta pruebas sobre la cola basada en arreglo dinámico. Se miden
     * operaciones de encolado y desencolado consecutivas para un conjunto de
     * elementos.
     *
     * @param size número de elementos a encolar y desencolar
     * @return lista de resultados
     */
    private static List<Result> benchmarkQueue(int size) {
        List<Result> results = new ArrayList<>();
        MyQueue<Integer> queue = new MyQueueArrayList<>(Math.max(1, size));
        long start, end;

        // Medir encolado
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            queue.enqueue(i);
        }
        end = System.nanoTime();
        results.add(new Result("MyQueueArrayList", "enqueue", size, nanosToMillis(end - start)));

        // Medir desencolado
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            try {
                Integer val = queue.dequeue();
                if (val == null) {
                    throw new IllegalStateException("dequeue() devolvió null antes de tiempo");
                }
            } catch (MyQueueUnderFlowException e) {
                throw new IllegalStateException("La cola se vació de forma inesperada", e);
            }
        }
        end = System.nanoTime();
        results.add(new Result("MyQueueArrayList", "dequeue", size, nanosToMillis(end - start)));

        return results;
    }

    /**
     * Ejecuta el algoritmo de planificación de semestres para un conjunto de
     * materias con prerequisitos generados aleatoriamente sin ciclos. Para
     * mantener el tiempo de ejecución razonable se utilizan como máximo dos
     * prerequisitos por materia.
     *
     * @param size número de materias del plan
     * @return resultado único con el tiempo de ejecución total
     */
    private static Result benchmarkSemesterCalculator(int size) {
        // Generar materias
        List<Materia> materias = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Materia m = new Materia();
            m.setIdMateria(i);
            m.setNomMateria("Materia " + i);
            m.setNumCreditos(3);
            materias.add(m);
        }

        // Generar prerequisitos de forma acíclica: cada materia i puede tener hasta 2 prereqs entre [0, i-1]
        List<Prerequisito> prereqs = new ArrayList<>();
        Random rnd = new Random(12345);
        for (int i = 1; i < size; i++) {
            int numPrereqs = rnd.nextInt(3); // 0,1 o 2 prerequisitos
            HashSet<Integer> escogidos = new HashSet<>(numPrereqs);
            while (escogidos.size() < numPrereqs && escogidos.size() < i) {
                int prereqIndex = rnd.nextInt(i);
                if (escogidos.add(prereqIndex)) {
                    Materia materia = materias.get(i);
                    Materia prereq = materias.get(prereqIndex);
                    prereqs.add(new Prerequisito(materia, prereq));
                }
            }
        }

        KahnSemesterCalculator calculator = new KahnSemesterCalculator();
        long start = System.nanoTime();
        // Limitamos a 8 materias por semestre como valor arbitrario para la prueba.
        KahnSemesterCalculator.SemesterPlan plan = calculator.calcularSemestresMinimos(materias, prereqs, 8);
        long end = System.nanoTime();

        // Verificar que no haya ciclo (para tamaños grandes es improbable con prereqs previos)
        if (plan.hasCycle()) {
            throw new IllegalStateException("Se generó un ciclo inesperado en los prerequisitos para tamaño " + size);
        }

        return new Result("KahnSemesterCalculator", "calcularSemestresMinimos", size, nanosToMillis(end - start));
    }

    /**
     * Convierte nanosegundos a milisegundos como valor de coma flotante para
     * mayor legibilidad.
     *
     * @param nanos valor en nanosegundos
     * @return valor en milisegundos
     */
    private static double nanosToMillis(long nanos) {
        return nanos / 1_000_000.0;
    }

    /**
     * Escribe los resultados a un archivo CSV. Cada línea contiene los
     * encabezados "Estructura", "Operacion", "Tamaño" y "Tiempo_ms".
     *
     * @param results lista de resultados a escribir
     * @param filename nombre del archivo destino
     */
    private static void writeResultsCSV(List<Result> results, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("estructura,operacion,tamano,tiempo_ms");
            for (Result r : results) {
                pw.printf("%s,%s,%d,%.3f%n", r.structure, r.operation, r.size, r.timeMs);
            }
        } catch (IOException e) {
            System.err.println("No se pudo escribir el archivo de resultados: " + e.getMessage());
        }
    }
}
