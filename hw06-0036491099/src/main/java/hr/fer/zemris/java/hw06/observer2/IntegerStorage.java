package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents basic storage of int values. It is used as a subject in Observer pattern. Observers must be
 * of type {@link IntegerStorageObserver}.
 */
public class IntegerStorage {

    /**
     * This class is used for representing change in {@link IntegerStorage}.
     */
    public class IntegerStorageChange {

        /**
         * Storage on which change has happened.
         */
        private IntegerStorage istorage;

        /**
         * Old value.
         */
        private int oldValue;

        /**
         * New value.
         */
        private int newValue;

        /**
         * Basic constructor.
         *
         * @param istorage storage
         * @param oldValue new value
         * @param newValue old value
         */
        public IntegerStorageChange(IntegerStorage istorage, int oldValue, int newValue) {
            this.istorage = istorage;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        /**
         * Getter for storage.
         *
         * @return storage
         */
        public IntegerStorage getIstorage() {
            return istorage;
        }

        /**
         * Getter for old value.
         *
         * @return Old value
         */
        public int getOldValue() {
            return oldValue;
        }

        /**
         * Getter for new value.
         *
         * @return New value
         */
        public int getNewValue() {
            return newValue;
        }
    }

    /**
     * Current value of storage.
     */
    private int value;

    /**
     * List of observers.
     */
    private List<IntegerStorageObserver> observers;

    /**
     * Basic constructor.
     *
     * @param initialValue Initial value
     */
    public IntegerStorage(int initialValue) {
        this.observers = new ArrayList<>();
        this.value = initialValue;
    }

    /**
     * This method is used for adding observers to this subject.
     *
     * @param observer Observer
     */
    public void addObserver(IntegerStorageObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * This method is used for removing observers.
     *
     * @param observer Observer
     */
    public void removeObserver(IntegerStorageObserver observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    /**
     * This method is used for removing all observers.
     */
    public void clearObservers() {
        observers.clear();
    }

    /**
     * Getter for value.
     *
     * @return Value
     */
    public int getValue() {
        return value;
    }

    /**
     * Setter for value.
     *
     * @param value new value
     */
    public void setValue(int value) {
        if (this.value != value) {
            IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);

            this.value = value;
            if (observers != null) {

                int originalSize = observers.size();
                for (int i = 0; i < originalSize; i++) {
                    observers.get(i).valueChanged(change);
                    if (originalSize != observers.size()) {
                        originalSize--;
                        i--;
                    }

                }
            }
        }
    }
}