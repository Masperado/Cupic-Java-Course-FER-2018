package hr.fer.zemris.java.hw06.observer1;

/**
 * This inteface represents IntegerStorageObserver. Is is used as an abstract observer in Observer pattern, subject
 * in this case is {@link IntegerStorage}.
 */
public interface IntegerStorageObserver {

    /**
     * Method that will be called when {@link IntegerStorage} notifes observers for changes.
     *
     * @param istorage {@link IntegerStorage}
     */
    public void valueChanged(IntegerStorage istorage);
}