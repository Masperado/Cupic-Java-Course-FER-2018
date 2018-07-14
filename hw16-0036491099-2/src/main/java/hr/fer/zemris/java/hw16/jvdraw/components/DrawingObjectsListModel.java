package hr.fer.zemris.java.hw16.jvdraw.components;

import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

import javax.swing.*;

/**
 * This class represents list model for {@link DrawingModel}.
 */
public class DrawingObjectsListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

    /**
     * Drawing model.
     */
    private DrawingModel drawingModel;

    /**
     * Basic constructor.
     *
     * @param drawingModel Drawing model
     */
    public DrawingObjectsListModel(DrawingModel drawingModel) {
        this.drawingModel = drawingModel;

        drawingModel.addDrawingModelListener(this);
    }

    @Override
    public int getSize() {
        return drawingModel.getSize();
    }

    @Override
    public GeometricalObject getElementAt(int index) {

        return drawingModel.getObject(index);
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        fireIntervalAdded(source, index0, index1);
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        fireIntervalRemoved(source, index0, index1);
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        fireContentsChanged(source, index0, index1);
    }
}
