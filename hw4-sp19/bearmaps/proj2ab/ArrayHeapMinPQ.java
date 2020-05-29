package bearmaps.proj2ab;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class Node<T> {
        private T item;
        private double priority;
        public Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
        @Override
        public int hashCode() {
            return item.hashCode();
        }
        @Override
        public boolean equals(Object o) {
            return this.item.hashCode() == ((Node<T>) o).item.hashCode();
        }

        public double getPriority() {
            return this.priority;
        }
        public T getItem() {
            return this.item;
        }
        public void setPriority(double newPriority) {
            this.priority = newPriority;
        }

    }
    public T[] heap() {
        T[] heap = (T[]) new Object[size + 1];
        for (int i = 1; i <= size ; i++) {
            heap[i] = pq[i].getItem();
        }
        return heap;
    }
    private Node<T>[] pq;
    private Map<T, Integer> map;
    private int size;
    private static final int DEFAULT_INIT_CAPACITY = 11;
    public ArrayHeapMinPQ(int initCapacity) {
        //this.pq = (Node<T>[]) new Object[initCapacity];
        this.pq = new ArrayHeapMinPQ.Node[initCapacity];
        this.size = 0;
        this.map = new HashMap<>();
    }
    public ArrayHeapMinPQ() {
        this(DEFAULT_INIT_CAPACITY);
    }

    private double loadFactor() {
        return (double)this.size / (double)(pq.length - 1);
    }

    private void resize(int capacity) {
        Node<T>[] nodes = new ArrayHeapMinPQ.Node[capacity];
        //Node<T>[] nodes = (Node<T>[]) new Object[capacity];
        for (int i = 1; i <= size; i++) {
            nodes[i] = new Node<>(pq[i].getItem(), pq[i].getPriority());
        }
        pq = nodes;
    }
    private void resize() {
        double loadFactor = this.loadFactor();
        if (loadFactor < 0.25) {
            resize(pq.length / 2);
        }
        if (loadFactor >= 0.75) {
            resize(pq.length * 2);
        }
    }
    private void exchange(int i, int j) {
        Node tempNode = pq[i];
        pq[i] = pq[j];
        pq[j] = tempNode;
        map.put(pq[i].getItem(), i);
        map.put(pq[j].getItem(), j);
    }

    private boolean less(int i, int j) {
        return pq[i].getPriority() < pq[j].getPriority();
    }

    private void sink(int index) {
        while (index * 2 <= size) {
            int j = index * 2;
            if (j + 1 <= size && !less(j, j + 1)) {
                j++;
            }
            if (less(index, j)) {
                break;
            }
            exchange(index, j);
            index = j;
        }
    }

    private void swim(int index) {
        while (index > 1) {
            int parent = index / 2;
            if (less(parent, index)) {
                break;
            }
            exchange(parent, index);
            index = parent;
        }

    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            //System.out.println();
            throw new IllegalArgumentException();
        }
        pq[++size] = new Node<>(item, priority);
        map.put(item, size);
        swim(size);
        resize();
    }

    @Override
    public boolean contains(T item) {
        // O(log)
        return map.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        // O(log)
        T returnValue = pq[1].getItem();
        return returnValue;
    }

    @Override
    public T removeSmallest() {
        T returnValue = this.getSmallest();
        exchange(1, size);
        pq[size] = null;
        size--;
        sink(1);
        map.remove(returnValue);
        resize();

        return returnValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        // O(log)
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        Integer index = map.get(item);
        double originalPriority = pq[index].getPriority();
        pq[index].setPriority(priority);
        if (originalPriority < priority) {

            sink(index);
        } else {
            swim(index);
        }
    }
}
