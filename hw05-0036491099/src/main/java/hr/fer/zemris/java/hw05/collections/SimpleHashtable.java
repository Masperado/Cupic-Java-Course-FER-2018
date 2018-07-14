package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class represents Map-like collection which uses hashing for storing key-value pairs.
 * That means map operates in average O(1) complexity. For storing elements, this class uses {@link TableEntry}.
 * Key of entry can't be null. This class supports all basic operations like putting elements, getting elements,
 * removing elements and checking if elements are contained in Hashtable.
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

    /**
     * Array of TableEntry-ies which is used for storing elements of Hashtable.
     */
    private TableEntry<K, V>[] table;
    /**
     * Size of hashtable.
     */
    private int size;
    /**
     * Modification count of hashtable.
     */
    private int modificationCount;

    /**
     * Basic constructor.
     */
    public SimpleHashtable() {
        this(16);
    }

    /**
     * Constructor which initializes Hashtable with given capacity.
     *
     * @param capacity Initial capacity
     */
    public SimpleHashtable(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Veličina početne tablice mora biti veća od 1!");
        }

        capacity = (int) Math.pow(2, Math.ceil(Math.log(capacity) / Math.log(2)));
        table = (TableEntry<K, V>[]) new TableEntry[capacity];
    }

    /**
     * This method is used for putting key-value pairs in SimpleHashtable. If key already exists in hashtable,
     * value is updated and no new entry is created.
     *
     * @param key   key of entry
     * @param value value of entry
     */
    public void put(K key, V value) {
        if (putInTable(key, value, table)) {
            size++;
            modificationCount++;

            if (size > 0.75 * table.length) {
                doubleCapacity();
            }
        }
    }

    /**
     * This method is used for getting object at given key. If key doesn't exist in table, null is returned.
     *
     * @param key Key at which object will be given
     * @return Object at given key
     */
    public V get(Object key) {
        if (key == null) {
            return null;
        }

        int position = Math.abs(key.hashCode()) % table.length;
        TableEntry<K, V> entry = table[position];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    /**
     * This method returns size of hashtable.
     *
     * @return Size of hashtable
     */
    public int size() {
        return size;
    }

    /**
     * This method is used for checking if hashtable contains given key.
     *
     * @param key Key to be checked
     * @return True if contains, false otherwise
     */
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }

        int position = Math.abs(key.hashCode()) % table.length;
        TableEntry<K, V> entry = table[position];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    /**
     * This method is used for checking if hashtable contains given value.
     *
     * @param value Value to be checked
     * @return True if contains, false otherwise
     */
    public boolean containsValue(Object value) {
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                if (entry.value.equals(value)) {
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    /**
     * This method is used fore removing value for given key.
     *
     * @param key Key at which value will be removed
     */
    public void remove(Object key) {
        if (key == null) {
            return;
        }
        int position = Math.abs(key.hashCode()) % table.length;
        TableEntry<K, V> entry = table[position];
        TableEntry<K, V> beforeEntry = null;

        while (entry != null) {
            if (entry.key.equals(key)) {
                if (beforeEntry != null) {
                    beforeEntry.next = entry.next;
                } else {
                    table[position] = entry.next;
                }
                size--;
                modificationCount++;
                return;
            }
            beforeEntry = entry;
            entry = entry.next;
        }

    }

    /**
     * This class is used for checking if hashtable is empty.
     *
     * @return True if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                sb.append(entry.toString()).append(", ");
                entry = entry.next;
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method is used for clearing all elements in hashtable.
     */
    public void clear() {
        table = (TableEntry<K, V>[]) new TableEntry[table.length];
        size = 0;
    }

    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * This method is used for putting key-value pairs in given array of TableEntry. If key already exists in array,
     * value is updated and no new entry is created.
     *
     * @param key        key of entry
     * @param value      value of entry
     * @param tableToPut Table in which entries will be put in
     */
    private boolean putInTable(K key, V value, TableEntry<K, V>[] tableToPut) {
        if (key == null) {
            throw new NullPointerException("Ključ ne smije biti null!");
        }

        int position = Math.abs(key.hashCode()) % tableToPut.length;
        TableEntry<K, V> entry = tableToPut[position];

        if (entry == null) {
            tableToPut[position] = new TableEntry<>(key, value, null);
            return true;
        }

        while (entry.next != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return false;
            }
            entry = entry.next;
        }

        entry.next = new TableEntry<>(key, value, null);
        return true;

    }

    /**
     * This method is used for doubling capacity of Hashtable.
     */
    private void doubleCapacity() {
        TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                putInTable(entry.key, entry.value, newTable);
                entry = entry.next;
            }
        }
        table = newTable;
    }

    /**
     * This class represents TableEntry. It is used for storing key-value pairs that are used for storing elements in {@link SimpleHashtable}.
     * Every TableEntry has reference to next TableEntry so that means TableEntryies can be organized into linked-list.
     *
     * @param <K> Key type
     * @param <V> Value type
     */
    public static class TableEntry<K, V> {

        /**
         * Key of entry.
         */
        private K key;

        /**
         * Value of entry.
         */
        private V value;

        /**
         * Next entry.
         */
        private TableEntry<K, V> next;

        /**
         * Basic constructor.
         *
         * @param key   Key of entry
         * @param value Value of entry
         * @param next  Next entry
         */
        public TableEntry(K key, V value, TableEntry<K, V> next) {
            Objects.requireNonNull(key, "Ključ ne smije biti null!");
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Getter for key.
         *
         * @return Key
         */
        public K getKey() {
            return key;
        }

        /**
         * Getter for value.
         *
         * @return Value
         */
        public V getValue() {
            return value;
        }

        /**
         * Setter for value.
         *
         * @param value New value
         */
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * This class represents Iterator for {@link SimpleHashtable}. It is used for iterating through all elements of hashtable.
     */
    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

        /**
         * Iteration modification count.
         */
        private int iteratorModificationCount = SimpleHashtable.this.modificationCount;

        /**
         * Last returned element.
         */
        private TableEntry<K, V> currentElement;

        /**
         * Index of last returned element.
         */
        private int currentIndex;

        /**
         * Current index in array of TableEntries.
         */
        private int currentIndexInArray;

        private boolean removedCurrentElement = false;

        @Override
        public boolean hasNext() {
            if (iteratorModificationCount != modificationCount) {
                throw new ConcurrentModificationException("Kolekcija je modificirana dok traje iteriranje!");
            }

            return currentIndex < size;
        }

        @Override
        public TableEntry<K, V> next() {
            if (iteratorModificationCount != modificationCount) {
                throw new ConcurrentModificationException("Kolekcija je modificirana dok traje iteriranje!");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("Nema više elemenata!");
            }

            removedCurrentElement = false;

            if (currentElement == null) {
                while (currentElement == null) {
                    currentElement = table[currentIndexInArray];
                    if (currentElement == null) {
                        currentIndexInArray++;
                    }
                }
                currentIndex++;
                return currentElement;
            }

            if (currentElement.next != null) {
                currentElement = currentElement.next;
                currentIndex++;
                return currentElement;
            } else {
                currentElement = table[++currentIndexInArray];
                while (currentElement == null) {
                    currentElement = table[currentIndexInArray];
                    if (currentElement == null) {
                        currentIndexInArray++;
                    }
                }
                currentIndex++;
                return currentElement;
            }

        }

        @Override
        public void remove() {
            if (removedCurrentElement){
                throw new IllegalStateException("Uklanjanje trenutnog elementa više od 2 puta!");
            }

            SimpleHashtable.this.remove(currentElement.key);
            removedCurrentElement = true;
            currentElement = null;
            iteratorModificationCount++;
            currentIndex--;
        }
    }

}
