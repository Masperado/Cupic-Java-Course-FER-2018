package hr.fer.zemris.java.hw16.jvdraw.interfaces;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * This interface represents drawing model. It is used for storing {@link GeometricalObject}.
 */
public interface DrawingModel extends GeometricalObjectListener {

    /**
     * Getter for size.
     *
     * @return Size
     */
    int getSize();

    /**
     * This method is used for getting {@link GeometricalObject} at given index.
     *
     * @param index Index
     * @return Object at given index
     */
    GeometricalObject getObject(int index);

    /**
     * This method is used for adding new object to model.
     *
     * @param object New {@link GeometricalObject}
     */
    void add(GeometricalObject object);

    /**
     * This method is used for removing object at given index.
     *
     * @param index Index
     */
    void remove(int index);

    /**
     * This method is used for removing object from model.
     *
     * @param object Object to be removed
     */
    void remove(GeometricalObject object);

    /**
     * This method is used for changing order of objects in model.
     *
     * @param object Object
     * @param offset Offset to be added
     */
    void changeOrder(GeometricalObject object, int offset);

    /**
     * This method is used for adding {@link DrawingModelListener} to model.
     *
     * @param l {@link DrawingModelListener}
     */
    void addDrawingModelListener(DrawingModelListener l);

    /**
     * This method is used for removing {@link DrawingModelListener} from model.
     *
     * @param l {@link DrawingModelListener}
     */
    void removeDrawingModelListener(DrawingModelListener l);
}