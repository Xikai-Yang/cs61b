public class DoublyLinkedList<E> {
    /**
     * constructor and copy constructor
     * get & recursively get
     * addFirst & addLast
     * removeFirst & removeLast
     * isEmpty
     * size
     */
    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;
        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }
        public E getElement() {
            return element;
        }
        public Node<E> getPrev() {
            return prev;
        }
        public Node<E> getNext() {
            return next;
        }
    }
    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;
    public DoublyLinkedList() {
        header = new Node<>(null,null,null);
        trailer = new Node<>(null,null,null);
        size = 0;
        header.next = trailer;
        trailer.prev = header;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return (size == 0);
    }
    public E first() {
        if (isEmpty()) {
            return null;
        }
        return header.next.element;
    }
    public E last() {
        if (isEmpty()) {
            return null;
        }
        return trailer.prev.element;
    }
    private void addBetween(E item, Node<E> predecessor, Node<E> successor) {
        Node<E> newest = new Node<>(item,predecessor,successor);
        predecessor.next = newest;
        successor.prev = newest;
        size++;
    }
    public void addFirst(E item) {
        addBetween(item, header, header.next);
    }
    public void addLast(E item) {
        addBetween(item, trailer.prev, trailer);
    }
    private E remove(Node<E> node) {
        // requires it cannot be empty;
        E temp = node.element;
        Node<E> predecessor = node.prev;
        Node<E> successor = node.next;
        predecessor.next = successor;
        successor.prev = predecessor;
        size--;
        return temp;
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
        return remove(trailer.prev);
    }
    public static void main(String[] args) {
        DoublyLinkedList<Integer> DLL = new DoublyLinkedList<>();
        DLL.addFirst(15);
        DLL.addFirst(20);
        DLL.addLast(30);

    }

}
