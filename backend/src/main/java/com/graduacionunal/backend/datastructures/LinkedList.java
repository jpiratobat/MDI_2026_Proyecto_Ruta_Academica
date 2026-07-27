package com.graduacionunal.backend.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Simple singly linked list with head and tail pointers.
 * Supports O(1) push operations at both ends and iteration over elements.
 */
public class LinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // O(1)
    public void pushFront(T item) {
        Node<T> n = new Node<>(item);
        n.next = head;
        head = n;
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    // O(1)
    public T popFront() {
        if (head == null) {
            return null;
        }
        T value = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    // O(1)
    public void pushBack(T item) {
        Node<T> n = new Node<>(item);
        if (tail == null) {
            head = n;
            tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
    }

    // O(n)
    public T popBack() {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            T value = head.data;
            head = null;
            tail = null;
            size--;
            return value;
        }
        Node<T> current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        T value = current.next.data;
        current.next = null;
        tail = current;
        size--;
        return value;
    }

    // O(n)
    public boolean contains(T item) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.data, item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                T value = current.data;
                current = current.next;
                return value;
            }
        };
    }

    private static class Node<T> {
        private final T data;
        private Node<T> next;

        Node(T item) {
            data = item;
            next = null;
        }
    }
}
