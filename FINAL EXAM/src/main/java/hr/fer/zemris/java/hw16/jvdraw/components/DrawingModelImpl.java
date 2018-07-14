package hr.fer.zemris.java.hw16.jvdraw.components;

import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents implementation of {@link DrawingModel}.
 */
public class DrawingModelImpl implements DrawingModel {

    /**
     * List of {@link GeometricalObject}.
     */
    private List<GeometricalObject> objects = new ArrayList<>();

    /**
     * List of {@link DrawingModelListener}.
     */
    private List<DrawingModelListener> listeners = new ArrayList<>();

    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public GeometricalObject getObject(int index) {
        return objects.get(index);
    }

    @Override
    public void add(GeometricalObject object) {
        objects.add(object);
        object.addGeometricalObjectListener(this);
        for (DrawingModelListener listener : listeners) {
            listener.objectsAdded(this, objects.size(), objects.size());
        }
    }

    @Override
    public void remove(int index) {
        objects.get(index).removeGeometricalObjectListener(this);
        objects.remove(index);
        for (DrawingModelListener listener : listeners) {
            listener.objectsRemoved(this, index, index);
        }
    }

    @Override
    public void remove(GeometricalObject object) {
        int index = objects.indexOf(object);
        objects.remove(object);
        for (DrawingModelListener listener : listeners) {
            listener.objectsRemoved(this, index, index);
        }
    }

    @Override
    public void changeOrder(GeometricalObject object, int offset) {
        int indexOfElement = objects.indexOf(object);
        if ((indexOfElement + offset) < 0 || (indexOfElement + offset) > objects.size() - 1) {
            return;
        } else {
            Collections.swap(objects, indexOfElement, indexOfElement + offset);
            for (DrawingModelListener listener : listeners) {
                listener.objectsChanged(this, indexOfElement, indexOfElement + offset);
            }
        }
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }

    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {
        listeners.remove(l);
    }

    @Override
    public void geometricalObjectChanged(GeometricalObject o) {
        for (DrawingModelListener listener : listeners) {
            listener.objectsChanged(this, objects.indexOf(o), objects.indexOf(o));
        }
    }
}
