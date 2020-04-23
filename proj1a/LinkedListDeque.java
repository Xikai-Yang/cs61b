public class LinkedListDeque<E> {
    private static class Node<E> {
        private E elem;
        private Node<E> prev;
        private Node<E> next;
        public Node(E e, Node<E> p, Node<E> n) {
            elem = e;
            prev = p;
            next = n;
        }
    }
    private int size = 0;
    private Node<E> header;
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
    private void addBetween(E item, Node<E> predecessor, Node<E> successor) {
        Node<E> newest = new Node<>(item,predecessor,successor);
        predecessor.next = newest;
        successor.prev = newest;
        size++;
    }
    public void addFirst(E item) {
        addBetween(item,header,header.next);
    }
    public void addLast(E item) {
        addBetween(item, header.prev, header);
    }
    private E remove(Node<E> node) {
        E temp = node.elem;
        Node<E> predecessor = node.prev;
        Node<E> successor = node.next;
        predecessor.next = successor;
        successor.prev = predecessor;
        size--;
        return temp;
    }
    public boolean isEmpty() {
        return (size == 0);
    }
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }
        return remove(header.next);
    }
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }
        return remove(header.prev);
    }
    public int size() {
        return size;
    }
    public E get(int index) {
        if (index > size - 1) {
            return null;
        }
        // make sure it couldn't be empty
        Node<E> temp = header.next;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.elem;
    }
    private E getRecursive(Node<E> node, int index) {
        if (index == 0) {
            return node.elem;
        }
        return getRecursive(node.next, index - 1);
    }

    public E getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }
        // not empty
        return getRecursive(header.next,index);
    }

    public void PrintDeque() {
        if (isEmpty()) {
            return;
        }
        Node<E> temp = header.next;
        while (temp != header) {
            System.out.print(temp.elem + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}
