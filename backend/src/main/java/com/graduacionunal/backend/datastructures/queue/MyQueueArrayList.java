package com.graduacionunal.backend.datastructures.queue;

public class MyQueueArrayList<T> implements MyQueue<T> {
    private int size;
    private int capacity;
    private T arreglo[];
    private int front; // Indice del primer elemento (inicio de la cola)
    private int rear; // Indice donde se insertará el próximo elemento (final de la cola)

    public MyQueueArrayList(int capacity) {
        // Constructor del la cola con arreglo
        arreglo = (T[]) new Object[capacity];
        this.capacity = capacity;
        size = 0;
        front = 0;
        rear = 0;
    }

    public void enqueue(T item) {
        if (size == capacity) {
            // Si el size es igual a la capacidad se redimensiona
            resize();
        }

        arreglo[rear] = item;

        // Se actualiza rear de forma circular
        rear = (rear + 1) % capacity;
        size++;
    }

    private void resize() {
        int nuevaCapacidad = capacity * 2;
        T nuevoArreglo[] = (T[]) new Object[nuevaCapacidad];

        // Copiamos los elementos actuales en orden correcto, respetando el índice circular
        int indice = front;
        for (int i = 0; i < size; i++) {
            nuevoArreglo[i] = arreglo[indice];
            indice = (indice + 1) % capacity;
        }

        // Una vez copiados, reiniciamos los índices
        front = 0; // El primer elemento ahora está en posición 0
        rear = size; // El siguiente espacio libre está después del último elemento
        capacity = nuevaCapacidad;
        arreglo = nuevoArreglo;
    }

    public T dequeue() throws MyQueueUnderFlowException {
        if (isEmpty()) {
            throw new MyQueueUnderFlowException("No se puede hacer dequeue(): la cola está vacía");
        }

        T valor = arreglo[front];

        // Se mueve 'front' una posición hacia adelante, respetando la circularidad
        front = (front + 1) % capacity;
        size--;
        return valor;
    }

    public T front() throws MyQueueUnderFlowException {
        if (isEmpty()) {
            throw new MyQueueUnderFlowException("No se puede hacer front(): la cola está vacía");
        }

        return arreglo[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
