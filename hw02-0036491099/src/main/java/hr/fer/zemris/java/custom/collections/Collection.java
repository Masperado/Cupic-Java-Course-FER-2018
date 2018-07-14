package hr.fer.zemris.java.custom.collections;


/**
 * This class represents collection. It is used as a 'abstract' class for storing elements.
 */
public class Collection {

    /**
     * Basic constructor.
     */
    protected Collection() {

    }

    /**
     * This method checks if collection is empty.
     *
     * @return True if empty, false otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * This method returns size of collection.
     *
     * @return Size of collection
     */
    public int size() {
        return 0;
    }

    /**
     * This method adds object to collection.
     *
     * @param value Object that will be added to collection.
     */
    public void add(Object value) {

    }

    /**
     * This method checks if collection contains given object.
     *
     * @param value Object that will be checked if it is in the collection
     * @return True if collection contains object, false otherwise
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * This method removes given object from collection.
     *
     * @param value Object to be removed
     * @return True if object is removed, false otherwise
     */
    public boolean remove(Object value) {
        return false;
    }

    /**
     * This method converts collection to Object array.
     *
     * @return Object array representation of collection
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * This method calls {@link Processor} for each element of collection.
     *
     * @param processor {@link Processor} that will be called
     */
    public void forEach(Processor processor) {

    }

    /**
     * This method adds all elements of given collection to this collection.
     *
     * @param collection Collection which elements will be added.
     */
    public void addAll(Collection collection) {

        /*
          This class represents processor that is used for adding values to this collection.
         */
        class addProcessor extends Processor {

            @Override
            public void process(Object value) {
                add(value);
            }
        }

        collection.forEach(new addProcessor());

    }

    /**
     * This method is used for clearing all elements from collection.
     */
    public void clear() {

    }
}
