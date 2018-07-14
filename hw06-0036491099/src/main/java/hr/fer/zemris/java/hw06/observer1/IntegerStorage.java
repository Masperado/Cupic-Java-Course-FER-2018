package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents basic storage of int values. It is used as a subject in Observer pattern. Observers must be
 * of type {@link IntegerStorageObserver}.
 */
public class IntegerStorage {

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
            this.value = value;
            if (observers != null) {

                int originalSize = observers.size();
                for (int i = 0; i < originalSize; i++) {
                    observers.get(i).valueChanged(this);
                    if (originalSize != observers.size()) {
                        originalSize--;
                        i--;
                    }

                }
            }
        }
    }
}