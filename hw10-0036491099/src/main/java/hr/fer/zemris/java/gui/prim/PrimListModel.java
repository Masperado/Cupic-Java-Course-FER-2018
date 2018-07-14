package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents PrimListModel. It is used as a {@link ListModel} implementation for list of prime numbers.
 */
public class PrimListModel implements ListModel<Integer> {

    /**
     * List in which prime number will be stored.
     */
    private List<Integer> elements = new ArrayList<>();

    /**
     * List of listeners.
     */
    private List<ListDataListener> listeners = new ArrayList<>();

    /**
     * Last generated prime.
     */
    private int currentPrime = 1;

    /**
     * Basic constructor.
     */
    public PrimListModel() {
        elements.add(1);
    }

    @Override
    public int getSize() {
        return elements.size();
    }

    @Override
    public Integer getElementAt(int index) {
        return elements.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /**
     * This method is used for generating next prime number.
     */
    public void next() {
        while (true) {
            currentPrime++;
            int numberOfDivisors = 0;
            for (int i = 1; i < currentPrime; i++) {
                if (currentPrime % i == 0) {
                    numberOfDivisors++;
                }
            }
            if (numberOfDivisors == 1) {
                elements.add(currentPrime);
                break;
            }
        }

        ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize());
        notifyObservers(event);
    }

    /**
     * This method is used for notifying all observers.
     *
     * @param event {@link ListDataEvent}
     */
    private void notifyObservers(ListDataEvent event) {
        for (ListDataListener l : listeners) {
            l.intervalAdded(event);
        }
    }


}
