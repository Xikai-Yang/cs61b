import javax.swing.text.html.HTMLDocument;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertTrue;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private class Node {
        private K key;
        private V value;
        private Node right = null;
        private Node left = null;
        private int N;
        public Node(K key, V val, int N) {
            this.key = key;
            this.value = val;
            this.N = N;
        }
    }
    private Node root = null;

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        V value = get(root, key);
        if (value == null) {
            return false;
        }
        return true;
    }

    private V get(Node root, K key) {
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            return get(root.left, key);
        }
        if (cmp > 0) {
            return get(root.right, key);
        }
        return root.value;
    }
    @Override
    public V get(K key) {
        return get(root, key);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return node.N;
    }
    @Override
    public int size() {
        return size(root);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            x.value = value;
        }
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        }
        if (cmp > 0) {
            x.right = put(x.right, key, value);
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private void keySet(Node x, Set<K> set) {
        if (x == null) {
            return;
        }
        keySet(x.left, set);
        set.add(x.key);
        keySet(x.right, set);
    }
    @Override
    public Set<K> keySet() {
        Set<K> myKeySet = new TreeSet<>();
        keySet(root, myKeySet);
        return myKeySet;
    }
    // fairly challenging
    private V helperRemove(Node x, K key) {
        if (x == null) {
            return null;
        }

    }
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }


    private class MapIterator implements Iterator<K> {
        public Set<K> mySet;
        public MapIterator() {
            mySet = keySet();
        }
        @Override
        public boolean hasNext() {
            return mySet.iterator().hasNext();
        }

        @Override
        public K next() {
            return mySet.iterator().next();
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new MapIterator();
    }

    private void printInOrder(Node x) {
        if (x == null) {
            return;
        }
        printInOrder(x.left);
        System.out.println(x.key);
        printInOrder(x.right);
    }
    public void printInOrder() {
        printInOrder(root);
    }
    public static void main(String[] args) {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1+i);
            //make sure put is working via containsKey and get
            assertTrue( null != b.get("hi" + i) && (b.get("hi"+i).equals(1+i))
                    && b.containsKey("hi" + i));
        }
        Set<String> mySet = b.keySet();
        int count = 0;
        for (String item : mySet) {
            count++;
            System.out.println(item);
        }
        assertTrue(count == 455);
    }

}
