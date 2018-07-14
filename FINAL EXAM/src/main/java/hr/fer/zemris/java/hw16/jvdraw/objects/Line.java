package hr.fer.zemris.java.hw16.jvdraw.objects;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.editors.LineEditor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents line in {@link hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel}.
 */
public class Line extends GeometricalObject {

    /**
     * List of listeners.
     */
    private List<GeometricalObjectListener> listeners = new ArrayList<>();

    /**
     * Start point.
     */
    private Point startPoint;

    /**
     * End point.
     */
    private Point endPoint;

    /**
     * Color of line.
     */
    private Color color;

    /**
     * Basic constructor.
     *
     * @param startPoint Start point
     * @param endPoint   End point
     * @param color      Color
     */
    public Line(Point startPoint, Point endPoint, Color color) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.color = color;
    }


    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new LineEditor(this);
    }

    @Override
    public void addGeometricalObjectListener(GeometricalObjectListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    @Override
    public void removeGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.remove(l);
    }


    @Override
    public String toString() {
        return String.format("   Line (%d,%d)-(%d,%d)   ", startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }


    /**
     * This method is used for parsing line from string.
     *
     * @param line String
     * @return Line
     */
    public static Line parse(String line) {
        String[] args = line.split(" ");
        int sx = Integer.parseInt(args[1]);
        int sy = Integer.parseInt(args[2]);
        int ex = Integer.parseInt(args[3]);
        int ey = Integer.parseInt(args[4]);
        int r = Integer.parseInt(args[5]);
        int g = Integer.parseInt(args[6]);
        int b = Integer.parseInt(args[7]);

        return new Line(new Point(sx, sy), new Point(ex, ey), new Color(r, g, b));
    }

    /**
     * This method is used for writing line to string.
     *
     * @param line line
     * @return String representation
     */
    public static String writeToString(Line line) {
        Point start = line.getStartPoint();
        Point end = line.getEndPoint();
        Color color = line.getColor();
        return String.format("LINE %d %d %d %d %d %d %d\r\n", start.x, start.y, end.x, end.y, color.getRed(),
                color.getGreen(), color.getBlue());
    }

    /**
     * Getter for start point.
     *
     * @return Start point
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * Getter for end point.
     *
     * @return End point
     */
    public Point getEndPoint() {
        return endPoint;
    }

    /**
     * Getter for color.
     *
     * @return Color
     */
    public Color getColor() {
        return color;
    }


    /**
     * Setter for start point.
     *
     * @param startPoint Start point
     */
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
        notifyListeners();
    }

    /**
     * Setter for end point.
     *
     * @param endPoint End point
     */
    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
        notifyListeners();
    }

    /**
     * Setter for color.
     *
     * @param color Color
     */
    public void setColor(Color color) {
        this.color = color;
        notifyListeners();
    }


    /**
     * This method is used for notifying listeners.
     */
    private void notifyListeners() {
        for (GeometricalObjectListener l : listeners) {
            l.geometricalObjectChanged(this);
        }
    }
}
