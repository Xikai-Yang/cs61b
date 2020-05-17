import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MyTrieSet implements TrieSet61B{

    private static final int R = 256; // ASCII
    private Node root;
    private static class Node {
        public boolean isKey;
        public Node next[];
        public Node() {
            this.isKey = false;
            this.next = new Node[R];
        }
    }
    public MyTrieSet() {
        root = new Node();

    }
    @Override
    public void clear() {
        root = new Node();
    }

    private Node get(String key, Node x, int length) {
        if (x == null) {
            return null;
        }
        // if x is not null ?
        if (length == key.length()) {
            return x;
        }
        char item = key.charAt(length);
        return get(key, x.next[item], length + 1);
    }

    @Override
    public boolean contains(String key) {
        Node returnNode = get(key, root, 0);
        return (returnNode != null && returnNode.isKey);
    }

    private Node add(String key, Node x, int length) {
        if (x == null) {
            x = new Node();
        }
        if (length == key.length()) {
            x.isKey = true;
            return x;
        }
        char item = key.charAt(length);
        x.next[item] = add(key, x.next[item], length + 1);
        return x;
    }
    @Override
    public void add(String key) {
        root = add(key, root, 0);
    }

    private void collect(String pre, Node x, List<String> list) {
        if (x == null) {
            return;
        }
        if (x.isKey) {
            list.add(pre);
        }
        for (int i = 0; i < R; i++) {
            if (x.next[i] != null) {
                collect(pre + (char)i, x.next[i], list);
            }
        }
    }
    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node preNode = get(prefix, root, 0);
        List<String> stringList = new ArrayList<>();
        collect(prefix, preNode, stringList);
        return stringList;
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        MyTrieSet t = new MyTrieSet();
        t.add("hello");
        t.add("hi");
        t.add("help");
        t.add("zebra");
        List<String> list= t.keysWithPrefix("h");
        System.out.println(list);
    }
}
