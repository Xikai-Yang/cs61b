public class ArrayDeque<T> {
    private static final int DEFAULT_CAPACITY = 8;
    private int size = 0;
    private T[] data;
    private int first = 0;
    public ArrayDeque() {
        size = 0;
        first = 0;
        data = (T[]) new Object[DEFAULT_CAPACITY];
    }
    /**
     * addFirst && addLast
     * isEmpty size
     * removeFirst && removeLast
     * get(int index)
     * all constant time
     */
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return (size == 0);
    }
    public void addFirst(T item) {
        // don't forget to resize!
        if(size == data.length) {
            resize(data.length * 2);
        }
        data[first] = item;
        first = (first - 1 + data.length) % data.length;
        size++;
    }
    public void addLast(T item) {
        // resize!
        if(size == data.length) {
            resize(data.length * 2);
        }
        int last = (first + 1 + size) % data.length;
        data[last] = item;
        size++;
    }
    private void resize(int capacity) {
        T[] newdata = (T[]) new Object[capacity];
        int current_first = (first + 1) % data.length;
        int first_part = data.length - current_first;
        int second_part = data.length - first_part;
        System.arraycopy(data, current_first, newdata,
                0, first_part);
        System.arraycopy(data, 0, newdata, first_part, second_part);
        data = newdata;
        first = data.length - 1;
    }
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int current_first = (first + 1) % data.length;
        first = current_first;
        T temp = data[current_first];
        size--;
        shrink();
        return temp;
    }
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int last = (first + 1 + size) % data.length;
        last = (last - 1 + data.length) % data.length;
        T temp = data[last];
        size--;
        shrink();
        return temp;
    }
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        int location = (first + 1 + index) % data.length;
        return data[location];
    }
    private void shrink() {
        double load_factor = (double) size / (double) data.length;
        boolean condition = (data.length >= 16) && (load_factor < 0.25);
        if (condition) {
            int current_first = (first + 1) % data.length;
            T[] newdata = (T[]) new Object[data.length / 2 + 1];
            for (int i = 0; i < size; i++) {
                newdata[i] = data[current_first];
                current_first = (current_first + 1) % data.length;
            }
            data = newdata;
            first = newdata.length - 1;
        }
    }
    public void printDeque() {
        int current_first = (first + 1 + data.length) % data.length;
        int ptr = current_first;
        int current_last = (first + size) % data.length;
        while (true) {
            System.out.print(data[ptr] + " ");
            ptr = (ptr + 1) % data.length;
            if (ptr == current_last) {
                break;
            }
        }
    }
    
}
