package hr.fer.zemris.java.custom.collections;

/**
 * This class represents object stack. It is used for storing objects in
 * stack. It uses
 * {@link ArrayIndexedCollection} for storing elements.
 */
public class ObjectStack {

    /**
     * Collection in which elements will be stored.
     */
    private ArrayIndexedCollection collection = new ArrayIndexedCollection();

    /**
     * This method checks if stack is empty.
     *
     * @return True if stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * This method returns size of stack.
     *
     * @return Size of stack.
     */
    public int size() {
        return collection.size();
    }

    /**
     * This method is used for pushing elements to stack.
     *
     * @param value Element that will be pushed to stack
     */
    public void push(Object value) {
        collection.add(value);
    }

    /**
     * This method is used for popping elements from stack.
     *
     * @return Popped element
     */
    public Object pop() {
        if (size() == 0) {
            throw new EmptyStackException("Stog je prazan!");
        }

        Object value = peek();
        collection.remove(size() - 1);

        return value;
    }

    /**
     * This method is used for peeking elements from stack.
     *
     * @return Peeked element
     */
    public Object peek() {
        if (size() == 0) {
            throw new EmptyStackException("Stog je prazan");
        }

        return collection.get(size() - 1);
    }

    /**
     * This method is used for clearing stack.
     */
    public void clear() {
        collection.clear();
    }

}
