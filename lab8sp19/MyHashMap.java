import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private int initialSize;
    private double loadFactor;
    private ArrayList[] buckets;
    private int size;

    private class Entry {
        private K key;
        private V value;
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() {
            return this.key;
        }
        public V getValue() {
            return this.value;
        }
        public void setValue(V value) {
            this.value = value;
        }
    }

    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }
    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }
    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.buckets = (ArrayList<Entry>[]) new ArrayList[initialSize];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<Entry>();
        }
    }
    private void resize(int capacity) {
        int threshold = (int) (buckets.length * loadFactor);
        if (size > threshold) {
            ArrayList[] newBuckets = (ArrayList<Entry>[]) new ArrayList[capacity];
            //Set[] newBuckets = new HashSet[capacity];
            for (int i = 0; i < newBuckets.length; i++) {
                newBuckets[i] = new ArrayList<Entry>();
            }
            for (int i = 0; i < this.buckets.length; i++) {
                for (int j = 0; j < buckets[i].size(); j++) {
                    Entry entry = ((Entry)buckets[i].get(j));
                    newBuckets[i].add(new Entry(entry.getKey(), entry.getValue()));
                }
            }
            this.buckets = newBuckets;
        }

    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % buckets.length;
    }
    @Override
    public void clear() {
        this.buckets = (ArrayList<Entry>[]) new ArrayList[initialSize];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<Entry>();
        }
        this.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int hashCode = hash(key);
        for (Object node : buckets[hashCode]) {
            Entry entry = (Entry) node;
            if (entry.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public V get(K key) {

        int hashCode = hash(key);
        for (Object node : buckets[hashCode]) {
            Entry entry = (Entry) node;
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        resize(size * 2);
        int hashCode = hash(key);
        for (Object node : buckets[hashCode]) {
            Entry entry = (Entry) node;
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }

        buckets[hashCode].add(new Entry(key, value));
        size++;
    }

    @Override
    public Set keySet() {
        Set<K> kSet = new HashSet<>();
        for (ArrayList list : buckets) {
            for (Object node : list) {
                Entry entry = (Entry) node;
                kSet.add(entry.getKey());
            }
        }
        return kSet;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }


    @Override
    public Iterator iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
    }
}
