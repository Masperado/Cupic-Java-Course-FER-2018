package hr.fer.zemris.java.hw16.jvdraw.objects;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.editors.FilledCircleEditor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents filled circle in {@link hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel}.
 */
public class FilledCircle extends GeometricalObject {

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
     * Foreground color.
     */
    private Color fgColor;

    /**
     * Background color.
     */
    private Color bgColor;

    /**
     * Constructor with center, end point, foreground color and background color.
     *
     * @param center   Center
     * @param endPoint Point
     * @param fgColor  Foreground color
     * @param bgColor  Background color
     */
    public FilledCircle(Point center, Point endPoint, Color fgColor, Color bgColor) {
        this.center = center;
        this.endPoint = endPoint;
        this.fgColor = fgColor;
        this.bgColor = bgColor;

        calculateRadius();
    }

    /**
     * Constructor with center, radius, foreground color and background color.
     *
     * @param center  Center
     * @param radius  Radius
     * @param fgColor Foreground color
     * @param bgColor Background color
     */
    public FilledCircle(Point center, int radius, Color fgColor, Color bgColor) {
        this.center = center;
        this.radius = radius;
        this.fgColor = fgColor;
        this.bgColor = bgColor;
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);

    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new FilledCircleEditor(this);
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
        return String.format("   Filled circle (%d,%d), %d, #%02X%02X%02X   ", center.x, center.y, radius, bgColor
                .getRed(), bgColor.getGreen(), bgColor.getBlue());

    }

    /**
     * This method is used for writing filled circle to string.
     *
     * @param circle Filled circle
     * @return String representation
     */
    public static String writeToString(FilledCircle circle) {
        Point center = circle.getCenter();
        int radius = circle.getRadius();
        Color fColor = circle.getFgColor();
        Color bColor = circle.getBgColor();
        return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d\r\n", center.x, center.y, radius, fColor.getRed(),
                fColor.getGreen(), fColor.getBlue(), bColor.getRed(), bColor.getGreen(), bColor.getBlue());
    }


    /**
     * This method is used for parsing filled circle from string.
     *
     * @param line String
     * @return Filled Circle
     */
    public static FilledCircle parse(String line) {
        String[] args = line.split(" ");

        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int radius = Integer.parseInt(args[3]);
        int r1 = Integer.parseInt(args[4]);
        int g1 = Integer.parseInt(args[5]);
        int b1 = Integer.parseInt(args[6]);
        int r2 = Integer.parseInt(args[7]);
        int g2 = Integer.parseInt(args[8]);
        int b2 = Integer.parseInt(args[9]);

        return new FilledCircle(new Point(x, y), radius, new Color(r1, g1, b1), new Color(r2, g2, b2));
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
     * Getter for foreground color.
     *
     * @return Color
     */
    public Color getFgColor() {
        return fgColor;
    }

    /**
     * Setter for color.
     *
     * @param fgColor foreground color
     */
    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
        notifyListeners();
    }

    /**
     * Getter for background color.
     *
     * @return Color
     */
    public Color getBgColor() {
        return bgColor;
    }

    /**
     * Setter for background color.
     *
     * @param bgColor Color
     */
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
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
