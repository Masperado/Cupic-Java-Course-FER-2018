package hr.fer.zemris.java.hw06.observer2;

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
    public void valueChanged(IntegerStorage.IntegerStorageChange change) {
        if (numberOfChanges < maxChanges) {
            System.out.println("Double value: " + change.getNewValue() * 2);
            numberOfChanges++;
        } else {
            change.getIstorage().removeObserver(this);
        }


    }
}
