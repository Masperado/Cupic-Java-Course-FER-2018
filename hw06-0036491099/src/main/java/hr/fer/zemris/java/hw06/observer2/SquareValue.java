package hr.fer.zemris.java.hw06.observer2;


/**
 * This class represents observer which prints squared current value of subject to console.
 */
public class SquareValue implements IntegerStorageObserver {

    @Override
    public void valueChanged(IntegerStorage.IntegerStorageChange change) {
        int value = change.getNewValue();

        System.out.println("Provided new value: " + value + ", square is " + value * value);
    }
}
