package hr.fer.zemris.java.hw06.observer1;

/**
 * This class represents observer which prints double current value of subject to console. It does that only limited
 * number of time that has to be given in constructor.
 */
public class DoubleValue implements IntegerStorageObserver {

    /**
     * Maximal number of changes on subject.
     */
    private int maxChanges;

    /**
     * Current number of changes.
     */
    private int numberOfChanges;

    /**
     * Basic constructor.
     *
     * @param i Maximal number of changes
     */
    public DoubleValue(int i) {
        this.maxChanges = i;
    }

    @Override
    public void valueChanged(IntegerStorage istorage) {
        if (numberOfChanges < maxChanges) {
            System.out.println("Double value: " + istorage.getValue() * 2);
            numberOfChanges++;
        } else {
            istorage.removeObserver(this);
        }


    }
}
