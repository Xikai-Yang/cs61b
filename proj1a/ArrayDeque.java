import java.util.Objects;

public class ArrayDeque<T> {
    private static final int default_capacity = 8;
    private T[] myarray;
    private int size;
    private int nextfirst;
    private int nextlast;
    public ArrayDeque() {
        myarray = (T[]) new Object[default_capacity];
        nextfirst = default_capacity/2 - 1;
        nextlast = default_capacity/2;
        size = 0;
    }
    private void shrink() {
        double load_factor = (double) size / (double) myarray.length;
        if((load_factor < 0.25) && (myarray.length >= 16)) {
            int length = nextlast - nextfirst;
            if(length < 4) {
                length = 4;
            }
            T[] newarray = (T[]) new Object[length*2];
            System.arraycopy(myarray,nextfirst+1,newarray,length-1,length);
            nextfirst = length - 2;
            nextlast = nextfirst + length;
            myarray = newarray;
        }
    }
    private void resize() {
        if(nextfirst < 0) {
            T[] newarray = (T[]) new Object[myarray.length * 2];
            System.arraycopy(myarray,0,newarray,myarray.length-1,myarray.length);
            nextfirst = myarray.length - 2;
            nextlast += myarray.length - 1;
            myarray = newarray;
        }
        if(nextlast >= myarray.length) {
            T[] newarray = (T[]) new Object[myarray.length * 2];
            System.arraycopy(myarray,0,newarray,0,myarray.length);
            nextlast = myarray.length;
            myarray = newarray;

        }
    }

    public void addFirst(T item) {
        shrink();
        myarray[nextfirst] = item;
        size++;
        nextfirst--;
        if(nextfirst < 0) {
            resize();
        }
    }
    public void addLast(T item) {
        shrink();
        myarray[nextlast] = item;
        size++;
        nextlast++;
        if(nextlast > myarray.length - 1) {
            resize();
        }
    }
    public boolean isEmpty() {
        return (size==0);
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        int start = nextfirst + 1;
        int end = nextlast - 1;
        for (int i = start; i <= end ; ++i) {
            System.out.print(myarray[i]+" ");
        }
        System.out.println();
    }
    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        nextfirst++;
        T temp = myarray[nextfirst];
        size--;
        shrink();
        return temp;
    }
    public T removeLast() {
        if(size == 0) {
            return null;
        }
        nextlast--;
        T temp = myarray[nextlast];
        size--;
        shrink();
        return temp;
    }
    public T get(int index) {
        return myarray[index+nextfirst+1];
    }

}
