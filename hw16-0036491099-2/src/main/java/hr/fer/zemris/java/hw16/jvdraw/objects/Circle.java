package hr.fer.zemris.java.hw16.jvdraw.objects;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.editors.CircleEditor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents circle in {@link hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel}.
 */
public class Circle extends GeometricalObject {

    /**
     * List of listeners,
     */
    private List<GeometricalObjectListener> listeners = new ArrayList<>();

    /**
     * End point.
     */
    private Point endPoint;

    /**
     * Radius.
     */
    private int radius;

    /**
     * Center point.
     */
    private Point center;

    /**
     * Color of circle.
     */
    private Color color;

    /**
     * Constructor with center, end point and color.
     *
     * @param center   Center
     * @param endPoint End point
     * @param color    Color
     */
    public Circle(Point center, Point endPoint, Color color) {
        this.center = center;
        this.endPoint = endPoint;
        this.color = color;

        calculateRadius();
    }

    /**
     * Constructor with center, radius and color.
     *
     * @param center Center
     * @param radius Radius
     * @param color  Color
     */
    public Circle(Point center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }


    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new CircleEditor(this);
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
        return String.format("   Circle (%d,%d), %d   ", center.x, center.y, radius);
    }


    /**
     * This method is used for writing circle to string.
     *
     * @param circle Circle
     * @return String representation
     */
    public static String writeToString(Circle circle) {
        Point center = circle.getCenter();
        int radius = circle.getRadius();
        Color color = circle.getColor();
        return String.format("CIRCLE %d %d %d %d %d %d\r\n", center.x, center.y, radius, color.getRed(), color.getGreen(),
                color.getBlue());
    }


    /**
     * This method is used for parsing circle from string.
     *
     * @param line String
     * @return Circle
     */
    public static Circle parse(String line) {

        String[] args = line.split(" ");

        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int radius = Integer.parseInt(args[3]);
        int r = Integer.parseInt(args[4]);
        int g = Integer.parseInt(args[5]);
        int b = Integer.parseInt(args[6]);

        return new Circle(new Point(x, y), radius, new Color(r, g, b));
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
     * Setter for end point.
     *
     * @param endPoint End point
     */
    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
        calculateRadius();
        notifyListeners();

    }

    /**
     * Getter for radius.
     *
     * @return Radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Setter for radius.
     *
     * @param radius Radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
        notifyListeners();
    }

    /**
     * Getter for center.
     *
     * @return Center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Setter for center.
     *
     * @param center Center
     */
    public void setCenter(Point center) {
        this.center = center;
        calculateRadius();
        notifyListeners();
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
     * Setter for color.
     *
     * @param color Color
     */
    public void setColor(Color color) {
        this.color = color;
        notifyListeners();
    }

    /**
     * This method is used for calculating radius of circle.
     */
    private void calculateRadius() {

        int xRadius = center.x - endPoint.x;
        int yRadius = center.y - endPoint.y;
        this.radius = (int) Math.sqrt(xRadius * xRadius + yRadius * yRadius);
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
