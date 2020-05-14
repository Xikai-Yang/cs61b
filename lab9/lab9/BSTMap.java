package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Xikai Yang
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        }
        if (cmp > 0) {
            return getHelper(key, p.right);
        }
        return p.value;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
        // throw new UnsupportedOperationException();
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            p.value = value;
        }
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        }
        if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        }
        return p;
        //throw new UnsupportedOperationException();
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (get(key) == null) {
            size++;
        }
        root = putHelper(key, value, root);
        //throw new UnsupportedOperationException();
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
        // throw new UnsupportedOperationException();
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////


    private void keySeyHelper(Set<K> set, Node x) {
        if (x == null) {
            return;
        }
        keySeyHelper(set, x.left);
        set.add(x.key);
        keySeyHelper(set, x.right);
    }
    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        keySeyHelper(set, root);
        return set;
    }

    private Node min(Node x) {
        // TODO
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }
    private Node deleteMin(Node x) {
        // TODO
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        return x;
    }
    private Node remove(K key, Node x) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(key, x.left);
        }
        if (cmp > 0) {
            x.right = remove(key, x.right);
        }
        if (cmp == 0) {
            if (x.left == null) {
                return x.right;
            }
            if (x.right == null) {
                return x.left;
            }
            // Hibbard Deletion
            Node temp = x;
            x = min(x.right);
            x.right = deleteMin(temp.right);
            x.left = temp.left;
        }
        return x;

    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V toBeReturned = get(key);
        if (toBeReturned != null) {
            size--;
            root = remove(key, root);
        }
        return toBeReturned;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V toBeReturned = get(key);
        if (toBeReturned.equals(value)) {
            size--;
            root = remove(key, root);
        }
        return toBeReturned;
    }

    @Override
    public Iterator<K> iterator() {
        Set<K> keySet = keySet();
        return keySet.iterator();
    }
}
