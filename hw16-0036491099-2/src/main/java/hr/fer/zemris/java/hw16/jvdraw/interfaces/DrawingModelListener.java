package hr.fer.zemris.java.hw16.jvdraw.interfaces;

/**
 * This interface represents {@link DrawingModelListener}. It is used as a listener to {@link DrawingModel}.
 */
public interface DrawingModelListener {

    /**
     * This method is called when object in added in moder.
     *
     * @param source Source of change
     * @param index0 Begin index
     * @param index1 End index
     */
    void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * This method is called when object is removed from model.
     *
     * @param source Source of change
     * @param index0 Begin index
     * @param index1 End index
     */
    void objectsRemoved(DrawingModel source, int index0, int index1);

    /**
     * This method is called when object is changed in model.
     *
     * @param source Source of change
     * @param index0 Begin index
     * @param index1 End index
     */
    void objectsChanged(DrawingModel source, int index0, int index1);
}