package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * This class represents linked list indexed collection. It is used for storing Objects. It uses linked list for
 * storing
 * elements.
 */
public class LinkedListIndexedCollection extends Collection {

    /**
     * This class represents list node. It is a basic building element of this collection.
     */
    private class ListNode {

        /**
         * Previous list node.
         */
        ListNode previous;

        /**
         * Next list node.
         */
        ListNode next;

        /**
         * Value of a node.
         */
        Object value;
    }

    /**
     * Current size of collection.
     */
    private int size;

    /**
     * First node of collection.
     */
    private ListNode first;

    /**
     * Last node of collection.
     */
    private ListNode last;

    /**
     * Basic constructor.
     */
    public LinkedListIndexedCollection() {
    }

    /**
     * Constructor which initializes collection and adds all element of given collection to new
     * collection.
     *
     * @param other Collection which elements will be added
     */
    public LinkedListIndexedCollection(Collection other) {
        Objects.requireNonNull(other, "Dana kolekcija ne može biti null!");
        addAll(other);
    }

    @Override
    public void add(Object value) {
        Objects.requireNonNull(value, "Vrijednost ne smije biti null!");

        if (first == null) {
            first = new ListNode();
            first.value = value;
        } else if (last == null) {
            last = new ListNode();
            last.value = value;
            first.next = last;
            last.previous = first;
        } else {
            last.next = new ListNode();
            last.next.previous = last;
            last.next.value = value;
            last = last.next;

        }
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

        if (index <= size / 2) {
            ListNode helper = first;

            for (int i = 0; i < size; i++) {
                if (i == index) {
                    return helper.value;
                }
                helper = first.next;
            }
        } else {
            ListNode helper = last;

            for (int i = size - 1; i >= 0; i--) {
                if (i == index) {
                    return helper.value;
                }

                helper = last.previous;
            }
        }

        throw new RuntimeException("Ova iznimka se nikad ne treba dogoditi!");
    }

    @Override
    public void clear() {
        first = null;
        last = null;
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
        Objects.requireNonNull(value, "Vrijednost ne smije biti null!");

        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Index nije valjan!");
        }

        if (first == null) {
            first = new ListNode();
            first.value = value;
        } else if (last == null) {
            last = new ListNode();
            last.value = value;
            first.next = last;
            last.previous = first;
        } else {
            ListNode helper = first;
            for (int i = 0; i < size; i++) {
                if (i == position) {
                    ListNode insertedNode = new ListNode();
                    insertedNode.value = value;
                    insertedNode.previous = helper.previous;

                    if (insertedNode.previous!=null){
                        insertedNode.previous.next = insertedNode;
                    }

                    insertedNode.next = helper;
                    insertedNode.next.previous = insertedNode;

                }
                helper = first.next;
            }
        }
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

        ListNode helper = first;
        for (int i = 0; i < size; i++) {
            if (helper.value.equals(value)) {
                return i;
            }
            helper = helper.next;
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

        ListNode helper = first;

        for (int i = 0; i < size; i++) {
            if (index == i) {
                helper.previous.next = helper.next;
                helper.next.previous = helper.previous;

            }
            helper = helper.next;

        }

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
        ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(this);
        return arrayIndexedCollection.toArray();
    }

    @Override
    public void forEach(Processor processor) {
        ListNode helper = first;
        for (int i = 0; i < size; i++) {
            processor.process(helper.value);
            helper = helper.next;
        }
    }

}
