package hr.fer.zemris.java.hw16.jvdraw.editors;

import javax.swing.*;

/**
 * This class represents Geometrical object editor. It is used for editing
 * {@link hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject}.
 */
public abstract class GeometricalObjectEditor extends JPanel {

    /**
     * This method is used for checking if editing is valid.
     */
    public abstract void checkEditing();

    /**
     * This method is used for applying changes.
     */
    public abstract void acceptEditing();
}