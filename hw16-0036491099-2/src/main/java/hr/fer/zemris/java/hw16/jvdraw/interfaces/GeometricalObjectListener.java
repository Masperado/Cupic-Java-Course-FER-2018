package hr.fer.zemris.java.hw16.jvdraw.interfaces;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * This interface represents listener for changes in {@link GeometricalObject}.
 */
public interface GeometricalObjectListener {

    /**
     * This method is called when change happens in {@link GeometricalObject}.
     *
     * @param o {@link GeometricalObject}
     */
    void geometricalObjectChanged(GeometricalObject o);
}