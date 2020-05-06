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
    // fairly challenging remove
    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }
    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * this remove method is relatively challenging
     * And it implements Hibbard deletion
     */
    private Node remove(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(x.left, key);
        } else if (cmp > 0) {
            x.right = remove(x.right, key);
        } else {
            if (x.left == null) {
                return x.right;
            } else if (x.right == null) {
                return x.left;
            }
            Node minNode = min(x.right);
            minNode.left = x.left;
            minNode.right = deleteMin(x.right);
            x = minNode;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    @Override
    public V remove(K key) {
        V val = get(root, key);
        if (val != null) {
            root = remove(root, key);
        }
        return val;
    }


    @Override
    public V remove(K key, V value) {
        V val = get(root, key);
        if (val.equals(value)) {
            root = remove(root, key);
            return val;
        }
        return null;
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
}
