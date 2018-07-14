package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * This class represents dictionary. It is used for storing key-value pairs.
 */
public class Dictionary {

    /**
     * This class represents Entry. It is used for a single key-value pair in dictionary.
     */
    private class Entry {

        /**
         * Key of Entry.
         */
        Object key;

        /**
         * Value of Entry.
         */
        Object value;

        /**
         * Basic constructor.
         *
         * @param key   Key of entry
         * @param value Value of entry
         */
        public Entry(Object key, Object value) {
            Objects.requireNonNull(key, "Ključ ne smije biti null!");
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(key, entry.key);
        }

        @Override
        public int hashCode() {

            return Objects.hash(key);
        }
    }


    /**
     * Collection used for storing {@link Entry} values.
     */
    private ArrayIndexedCollection collection;

    /**
     * Basic constructor.
     */
    public Dictionary() {
        this.collection = new ArrayIndexedCollection();
    }

    /**
     * This method is used for checking if dictionary is empty.
     *
     * @return True if empty, false otherwise.
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * This method is used for getting size of dictionary.
     *
     * @return Size of dictionary
     */
    public int size() {
        return collection.size();
    }

    /**
     * This method is used for clearing dictionary.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * This method is used for putting key-value pairs in dictionary.
     *
     * @param key   Key of entry
     * @param value Value of entry
     */
    public void put(Object key, Object value) {
        Object existingValue = get(key);

        if (existingValue == null) {
            collection.add(new Entry(key, value));
        } else {
            collection.remove(new Entry(key, existingValue));
            collection.add(new Entry(key, value));
        }

    }

    /**
     * This method is used for getting value at given key. If there is no value for given key, null is returned.
     *
     * @param key Key of Entry
     * @return Value of Entry
     */
    public Object get(Object key) {
        Object[] collectionArray = collection.toArray();

        for (Object entry : collectionArray) {
            if (((Entry) entry).key.equals(key)) {
                return ((Entry) entry).value;
            }
        }

        return null;
    }

}
