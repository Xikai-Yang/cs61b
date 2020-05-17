import java.util.ArrayList;
import java.util.List;

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

    /**
     * the first thing is to check if the node is null, if it is null, then it will return null
     * then the second thing is to check if the length == key.length(); if so, then we have found the node
     * otherwise, we just forward our recursion and increase the length variable
     */
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

    /**
     * this function is an addHelper function and returns a node as output
     * if Node x is a null node, then we just create a new one
     * if the length == key.length(), we will just return and previous operations can guarantee that this node
     * is not a null node
     */
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

    private String longestPrefixOf(String key, Node x, String prefix, int length) {
        if (x == null) {
            return prefix;
        }
        if (x.isKey) {
            prefix = key.substring(0, length);
        }
        if (length == key.length()) {
            return prefix;
        }
        char item = key.charAt(length);
        return longestPrefixOf(key, x.next[item], prefix, length + 1);
    }

    @Override
    public String longestPrefixOf(String key) {
        return longestPrefixOf(key, root, "", 0);
        // throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        String a = "abc";
        MyTrieSet trie = new MyTrieSet();
        trie.add("h");
        trie.add("hi");
        trie.add("hello");
        trie.add("help");
        trie.add("zebra");
        trie.add("homonym");
        trie.add("homophone");
        trie.add("homosexual");
        trie.add("she");
        trie.add("shells");

        System.out.println(trie.contains("hello")); // expect true
        System.out.println(trie.keysWithPrefix("h")); // expect [help, hello, hi, homophone, homosexual, homonym]
        //System.out.println(trie.longestCommonPrefixOf("hello")); // expect hel
        System.out.println(trie.keysWithPrefix("homo")); // expect [homophone, homosexual, homonym]
        System.out.println(trie.longestPrefixOf("helpful")); // expect help
        System.out.println(trie.longestPrefixOf("homogeneous")); // expect h
        System.out.println(trie.longestPrefixOf("shellsort"));
        System.out.println(trie.longestPrefixOf("shell"));
    }
}
