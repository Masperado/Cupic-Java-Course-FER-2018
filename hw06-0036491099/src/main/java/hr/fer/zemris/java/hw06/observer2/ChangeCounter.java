package hr.fer.zemris.java.hw06.observer2;

/**
 * This class represents observer that print to console how many time subject has changed.
 */
public class ChangeCounter implements IntegerStorageObserver {

    /**
     * Number of changes.
     */
    private int numberOfChanges;

    @Override
    public void valueChanged(IntegerStorage.IntegerStorageChange istorage) {
        System.out.println("Number of value changes since tracking: " + ++numberOfChanges);
    }
}
