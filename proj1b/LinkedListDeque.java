public class LinkedListDeque<T> implements Deque<T> {
    private static class Node<T> {
        private T elem;
        private Node<T> prev;
        private Node<T> next;
        public Node(T e, Node<T> p, Node<T> n) {
            elem = e;
            prev = p;
            next = n;
        }
    }
    private int size = 0;
    private Node<T> header;
    public LinkedListDeque() {
        header = new Node<>(null,null,null);
        header.next = header;
        header.prev = header;
        size = 0;
    }
    /**
     * constructor and copy constructor
     * get & recursively get
     * addFirst & addLast
     * removeFirst & removeLast
     * isEmpty
     * size
     */
    private void addBetween(T item, Node<T> predecessor, Node<T> successor) {
        Node<T> newest = new Node<>(item,predecessor,successor);
        predecessor.next = newest;
        successor.prev = newest;
        size++;
    }
    @Override
    public void addFirst(T item) {
        addBetween(item,header,header.next);
    }
    @Override
    public void addLast(T item) {
        addBetween(item, header.prev, header);
    }
    private T remove(Node<T> node) {
        T temp = node.elem;
        Node<T> predecessor = node.prev;
        Node<T> successor = node.next;
        predecessor.next = successor;
        successor.prev = predecessor;
        size--;
        return temp;
    }
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        return remove(header.next);
    }
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        return remove(header.prev);
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        // make sure it couldn't be empty
        Node<T> temp = header.next;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.elem;
    }
    private T getRecursive(Node<T> node, int index) {
        if (index == 0) {
            return node.elem;
        }
        return getRecursive(node.next, index - 1);
    }
    @Override
    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }
        // not empty
        return getRecursive(header.next,index);
    }
    @Override
    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        Node<T> temp = header.next;
        while (temp != header) {
            System.out.print(temp.elem + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}
