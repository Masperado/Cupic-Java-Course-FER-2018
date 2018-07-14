package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents map-stack like collection. While Map allows only to store for each key a single value, this
 * class allows user to store multiple values for same key and provides a stack-like abstraction. Keys have to be
 * instances of class {@link String}, and value instances of class {@link ValueWrapper}. Class has all method like
 * regular stack collection.
 */
public class ObjectMultistack {

    /**
     * This class is used for representing map entry for this class. It emulates linked-list which is used for
     * emulating stack.
     */
    private static class MultistackEntry {

        /**
         * Value of entry.
         */
        private ValueWrapper wrapper;

        /**
         * Next entry.
         */
        private MultistackEntry next;

        /**
         * Basic constructor.
         *
         * @param wrapper value of entry
         * @param next    next entry
         */
        public MultistackEntry(ValueWrapper wrapper, MultistackEntry next) {
            this.wrapper = wrapper;
            this.next = next;
        }


    }

    /**
     * Map used for storing stack entries.
     */
    private Map<String, MultistackEntry> multiStack = new LinkedHashMap<>();

    /**
     * This method is used for pushing given {@link ValueWrapper} under given {@link String} into this collection.
     *
     * @param name         Name of stack
     * @param valueWrapper Value that will be pushed
     */
    public void push(String name, ValueWrapper valueWrapper) {
        Objects.requireNonNull(name, "Ključ ne može biti null!");
        Objects.requireNonNull(valueWrapper, "Wrapper ne može biti null, ali null možeš umotati u wrappper!");

        MultistackEntry entry = multiStack.get(name);

        if (entry == null) {
            multiStack.put(name, new MultistackEntry(valueWrapper, null));
        } else {
            multiStack.remove(name);
            multiStack.put(name, new MultistackEntry(valueWrapper, entry));
        }

    }

    /**
     * This method is used for popping entry from stack.
     *
     * @param name Name of stack
     * @return Popped {@link ValueWrapper}
     * @throws EmptyStackException If stack is empty
     */
    public ValueWrapper pop(String name) {
        MultistackEntry entry = multiStack.get(name);

        if (entry == null) {
            throw new EmptyStackException();
        } else {
            multiStack.remove(name);
            multiStack.put(name, entry.next);
            return entry.wrapper;
        }
    }

    /**
     * This method is used for peeking entry from stack.
     *
     * @param name Name of stack
     * @return Peeked {@link ValueWrapper}
     * @throws EmptyStackException If stack is empty
     */
    public ValueWrapper peek(String name) {
        MultistackEntry entry = multiStack.get(name);

        if (entry == null) {
            throw new EmptyStackException();
        } else {
            return entry.wrapper;
        }
    }

    /**
     * This method is used for checking if stack for given name is empty.
     *
     * @param name Name of stack
     * @return True if empty, false otherwise
     */
    public boolean isEmpty(String name) {
        return multiStack.get(name) == null;
    }
}