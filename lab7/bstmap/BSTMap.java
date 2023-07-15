package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
//    int size = 0;
    private Node root;

    private class Node {
        public K key;
        public V value;
        public Node left, right;
        public int size;

        public Node(K k, V v, int size) {
            value = v;
            key = k;
            this.size = size;
        }
    }

    public BSTMap() {
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if(key == null) throw new IllegalArgumentException("containsKey with null key");
        return containsKey(root, key);
    }

    private boolean containsKey(Node X, K key) {
        if(X == null) return false;
        int temp = key.compareTo(X.key);
        if(temp == 0) return true;
        else if(temp < 0) return containsKey(X.left, key);
        else return containsKey(X.right, key);

    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node X, K key){
        if(X == null) return null;
        if(key == null) throw new IllegalArgumentException("call get() with a null key");
        int temp = key.compareTo(X.key);
        if(temp == 0) return X.value;
        else if (temp < 0) return get(X.left, key);
        else return get(X.right, key);
    }

    @Override
    public int size() {

        return size(root);
    }

    private int size(Node X) {
        if(X == null) return 0;
        return X.size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node X, K key, V value) {
        if(X == null) return new Node(key, value, 1);
        int temp = key.compareTo(X.key);
        if(temp < 0) X.left = put(X.left, key, value);
        else if (temp > 0) X.right = put(X.right, key, value);
        else X.value = value;
        X.size = size(X.left) + size(X.right) + 1;
        return X;
    }

    public void printInOrder() {
        StringBuilder sb = new StringBuilder();
        printInOrder(root, sb);
        System.out.println(sb.toString().trim());
    }

    private void printInOrder(Node X, StringBuilder sb) {
        if(X == null) return;
        sb.insert(0,X.key + " ");
        printInOrder(X.left, sb);
        printInOrder(X.right, sb);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
