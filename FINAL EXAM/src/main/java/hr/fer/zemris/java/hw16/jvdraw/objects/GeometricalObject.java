package hr.fer.zemris.java.hw16.jvdraw.objects;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectVisitor;

/**
 * This class represents geometrical object. It is used for representing objects in
 * {@link hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel}.
 */
public abstract class GeometricalObject {

    /**
     * This method is used for accepting {@link GeometricalObjectVisitor}.
     *
     * @param v {@link GeometricalObjectVisitor}
     */
    public abstract void accept(GeometricalObjectVisitor v);

    /**
     * This method is used for creating {@link GeometricalObjectEditor} for object.
     *
     * @return {@link GeometricalObjectEditor}
     */
    public abstract GeometricalObjectEditor createGeometricalObjectEditor();

    /**
     * This method is used for adding {@link GeometricalObjectVisitor} to object.
     *
     * @param l {@link GeometricalObjectVisitor}
     */
    public abstract void addGeometricalObjectListener(GeometricalObjectListener l);

    /**
     * This method is used for removing {@link GeometricalObjectVisitor} from object.
     *
     * @param l {@link GeometricalObjectVisitor}
     */
    public abstract void removeGeometricalObjectListener(GeometricalObjectListener l);


}
