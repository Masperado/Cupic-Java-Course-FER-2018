package hr.fer.zemris.java.hw06.observer1;

/**
 * This class represents observer which prints squared current value of subject to console.
 */
public class SquareValue implements IntegerStorageObserver {

    @Override
    public void valueChanged(IntegerStorage istorage) {
        int value = istorage.getValue();

        System.out.println("Provided new value: " + value + ", square is " + value * value);
    }
}
