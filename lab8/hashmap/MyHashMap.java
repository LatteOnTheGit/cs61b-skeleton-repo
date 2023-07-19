package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int m; //hash table size

    private int n; //number of key-value set
    private double loadFactor;

    private Set<K> keySet = null;

    /** Constructors */
    public MyHashMap() { this(16,0.75);}

    public MyHashMap(int initialSize) { this(initialSize, 0.75);}

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        if(initialSize < 1 || maxLoad <= 0.0) throw new IllegalArgumentException("initialSize should be greater than 1 and maxLoad mas be greater or equal than 0");
        buckets = createTable(initialSize);
        m = initialSize;
        loadFactor = maxLoad;

    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for(int i = 0; i < tableSize; i ++) {
            table[i] = createBucket();
        }
        return table;
    }

    private int getIndex(K key) {
        return getIndex(key, m);
    }

    private int getIndex(K key, int dividend) {
        return Math.floorMod(key.hashCode(), dividend);
    }

    private Node getNode(K key) {
        int i = getIndex(key);
        for (Node node : buckets[i]) {
            if(node.key.equals(key)) return node;
        }
        return null;
    }

    private void resize() {
        int newSize = m * 2;
        Collection<Node>[] newTable =  createTable(newSize);
        for(K key:this) {
            int index = getIndex(key, newSize);
            newTable[index].add(getNode(key));
        }
        m = newSize;
        buckets = newTable;
    }

    private class MapIterator implements Iterator<Node> {
        Node next, current;
        Iterator<Node> bucketIterator;
        int index = 0;

        public MapIterator() {
            next = current = null;
            findNextEntry();
        }

        private void findNextEntry() {
            while(index < buckets.length && buckets[index].isEmpty()) index++;
            if(index < buckets.length) {
                bucketIterator = buckets[index].iterator(); // first un-empty bucket start from index
                next = bucketIterator.next(); //next point to first item of that bucket
                index++; // point to next entry
            } else next = null;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Node next() {
            current = next;
            if(bucketIterator.hasNext()) next = bucketIterator.next();
            else findNextEntry();
            return current;
        }
    }

    private class keyIterator implements Iterator<K> {
        Iterator<Node> mapKeyIterator;

        public keyIterator() {
            mapKeyIterator = new MapIterator();
        }

        @Override
        public boolean hasNext() {
            return mapKeyIterator.hasNext();
        }

        @Override
        public K next() {
            return mapKeyIterator.next().key;
        }
    }

    private class KeySet extends AbstractSet<K> {

        @Override
        public Iterator<K> iterator() {
            return new keyIterator();
        }

        @Override
        public int size() {
            return m;
        }

        @Override
        public void clear() {
            MyHashMap.this.clear();
        }
    }

    @Override
    public void clear() {
        buckets = createTable(m);
        n = 0;
    }

    @Override
    public boolean containsKey(K key) {
        Node X = getNode(key);
        return X != null;
    }

    @Override
    public V get(K key) {
        if(buckets == null) return null;
        Node curNode = getNode(key);
        return curNode == null ? null:curNode.value;
    }

    @Override
    public int size() {
        return n; // return how many key-value set contains
    }

    @Override
    public void put(K key, V value) {
        if(value == null || key == null) throw new IllegalArgumentException();
        Node curNode = getNode(key);
        if(curNode == null) {
            int i = getIndex(key);
            buckets[i].add(createNode(key, value));
            n++;
            if((double) n/m > loadFactor) resize();
        } else {
            curNode.value = value;
        }
    }

    @Override
    public Set<K> keySet() {
        return keySet == null ? new KeySet() : keySet;
    }

    @Override
    public V remove(K key) {
        Node X = getNode(key);
        if(X != null) {
            int index = getIndex(key);
            buckets[index].remove(X);
            n--;
            V returnVal = X.value;
            return returnVal;
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if(get(key) == value) remove(key);
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new keyIterator();
    }


}
