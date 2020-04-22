public class LinkedListDeque<T> {
    private class Node{
        public T item;
        public Node next;
        public Node prev;
        public Node() {
            // how to code this constructor?
            item = (T)null;
            prev = null;
            next = null;
        }
        public Node(T item_,Node prev_,Node next_) {
            this.item = item_;
            this.prev = prev_;
            this.next = next_;
        }
    }
    private int size;
    private Node sentinel;

    public LinkedListDeque() {
        sentinel = new Node();
        size = 0;
    }
    public T getRecursive(int index) {
        if(index > size-1) {
            return null;
        }
        return getRecursive(sentinel,index+1);
    }
    public T getRecursive(Node node,int index) {
        if(index == 0) {
            return node.item;
        }
        return getRecursive(node.next,index - 1);
    }

    public void addFirst(T item) {
        if(size == 0) {
            sentinel.next = new Node(item,sentinel,null);
            sentinel.next.next = sentinel;
            sentinel.prev = sentinel.next;
        }
        else{
            sentinel.next.prev = new Node(item,sentinel,sentinel.next);
            sentinel.next = sentinel.next.prev;
        }
        size++;
    }
    public void addLast(T item) {
        if(size == 0) {
            sentinel.next = new Node(item,sentinel,null);
            sentinel.next.next = sentinel;
            sentinel.prev = sentinel.next;
        }
        else {
            sentinel.prev.next = new Node(item,sentinel.prev,null);
            sentinel.prev = sentinel.prev.next;
        }

        size++;
    }
    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        T temp = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return temp;
    }
    public T removeLast() {
        if(size == 0) {
            return null;
        }
        T temp = sentinel.prev.item;
        sentinel.prev.prev.next = null;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return temp;
    }
    public T get(int index) {
        if(index > (size - 1)) {
            return null;
        }
        int count = 0;
        Node temp = sentinel;
        while(true) {
            temp = temp.next;
            count++;
            if(count == index+1) {
                break;
            }
        }
        return temp.item;
    }


    public boolean isEmpty() {
        return (size == 0);
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        Node temp = sentinel;
        while(temp != sentinel.prev) {
            System.out.print(temp.next.item + " ");
            temp = temp.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        System.out.println(lld1.isEmpty());
        lld1.addFirst(10);
        System.out.println(lld1.isEmpty());
        lld1.removeFirst();

    }
    /*
    public static void main(String[] args) {
        LinkedListDeque<Integer> mydeque = new LinkedListDeque<>();
        mydeque.addFirst(100);
        mydeque.addFirst(150);
        mydeque.removeFirst();
        mydeque.addLast(200);
        mydeque.removeLast();

        System.out.println(mydeque.getRecursive(0));
        mydeque.printDeque();
    }
     */

}
