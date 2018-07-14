package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;


/**
 * This class represents array indexed collection. It is used for storing Objects. It uses array for storing elements.
 */
public class ArrayIndexedCollection extends Collection {

    /**
     * Current size of collection.
     */
    private int size;

    /**
     * Capacity of collection.
     */
    private int capacity;

    /**
     * Object array in which elements are stored.
     */
    private Object[] elements;

    /**
     * Default capacity of collection.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Default constructor which initializes collection with default capacity.
     */
    public ArrayIndexedCollection() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructor which initializes collection with given capacity.
     *
     * @param initialCapacity Initial capacity
     */
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Kapacitet ne može biti manji od 1!");
        }

        this.capacity = initialCapacity;
        this.elements = new Object[capacity];
    }


    /**
     * Constructor which initializes collection with default capacity and adds all element of given collection to new
     * collection.
     *
     * @param other Collection which elements will be added
     */
    public ArrayIndexedCollection(Collection other) {
        this(DEFAULT_CAPACITY, other);
    }

    /**
     * Constructor which initializes collection with given capacity and adds all element of given collection to new
     * collection.
     *
     * @param initialCapacity Initial capacity
     * @param other Collection which elements will be added
     */
    public ArrayIndexedCollection(int initialCapacity, Collection other) {
        Objects.requireNonNull(other, "Dana kolekcija ne može biti nulL!");

        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Kapacitet ne može biti manji od 1!");
        }

        this.capacity = initialCapacity;

        if (other.size() > capacity) {
            this.capacity = other.size();
        }
        this.elements = new Object[capacity];
        addAll(other);
    }


    @Override
    public void add(Object value) {
        Objects.requireNonNull(value, "Vrijednost ne smije biti nulL!");

        if (size == capacity) {
            capacity *= 2;
            elements = Arrays.copyOf(elements, capacity);
        }

        elements[size] = value;

        size++;
    }

    /**
     * This method is used for getting element at given index.
     *
     * @param index Index at which element will be given
     * @return Element at given index
     */
    public Object get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index nije valjan!");
        }

        return elements[index];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * This method is used for inserting element at given position. Element that was at given position is shifted
     * forward.
     *
     * @param value Element that will be inserted
     * @param position Position at which element will be inserted
     */
    public void insert(Object value, int position) {
        Objects.requireNonNull(value, "Vrijednost ne smije biti nulL!");

        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Index nije valjan!");
        }

        if (size == capacity) {
            capacity *= 2;
            elements = Arrays.copyOf(elements, capacity);
        }

        for (int i = size + 1; i < position; i++) {
            elements[i] = elements[i - 1];
        }

        elements[position] = value;

        size++;

    }

    /**
     * This method is used for finding index of given element.
     *
     * @param value Element of which index will be found
     *
     * @return Index of element if element exists, -1 otherwise
     */
    public int indexOf(Object value) {
        if (value == null) {
            return -1;
        }

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * This method is used for removing element at given index.
     *
     * @param index Index at which element will be removed
     */
    public void remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index nije valjan!");
        }

        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;

        size--;

    }

    @Override
    public boolean remove(Object value) {
        int indexOfValue = indexOf(value);

        if (indexOfValue == -1) {
            return false;
        } else {
            remove(indexOfValue);
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    @Override
    public Object[] toArray() {
        return elements;
    }

    @Override
    public void forEach(Processor processor) {
        for (int i = 0; i < size; i++) {
            processor.process(elements[i]);
        }
    }

}
