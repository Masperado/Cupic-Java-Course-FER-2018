package hr.fer.zemris.java.hw16.jvdraw.visitors;

import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

import java.awt.*;
import java.util.List;

/**
 * This class represents calculator for bounding box.
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

    /**
     * Left value.
     */
    private int left = Integer.MAX_VALUE;

    /**
     * Top value.
     */
    private int top = Integer.MAX_VALUE;

    /**
     * Right value.
     */
    private int right = 1;

    /**
     * Bottom value.
     */
    private int bottom = 1;

    @Override
    public void visit(Line line) {

        Point start = line.getStartPoint();
        Point end = line.getEndPoint();

        Point p = new Point(Math.min(start.x, end.x), Math.min(start.y, end.y));
        Dimension d = new Dimension(Math.abs(start.x - end.x), Math.abs(start.y - end.y));

        Rectangle r = new Rectangle(p, d);

        updateBox(r);

    }

    @Override
    public void visit(Circle circle) {
        visitCircle(circle.getCenter(), circle.getRadius());
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        visitCircle(filledCircle.getCenter(), filledCircle.getRadius());
    }

    @Override
    public void visit(FilledPolygon filledPolygon) {
        List<Point> pointList = filledPolygon.getPoints();

        if (pointList.size()==2){
            return;
        }

        int xPoly[] = new int[pointList.size()];
        int yPoly[] = new int[pointList.size()];

        for (int i=0; i<pointList.size();i++){
            xPoly[i]=pointList.get(i).x;
            yPoly[i]=pointList.get(i).y;
        }

        Polygon poly = new Polygon(xPoly,yPoly,xPoly.length);

        updateBox(poly.getBounds());
    }

    /**
     * This method is used for visiting circle.
     *
     * @param center Center point
     * @param radius Radius
     */
    private void visitCircle(Point center, int radius) {
        Point p = new Point(center.x - radius, center.y - radius);
        Dimension d = new Dimension(2 * radius, 2 * radius);
        Rectangle r = new Rectangle(p, d);

        updateBox(r);
    }

    /**
     * This method is used for updating bounding box.
     *
     * @param r Rectangle
     */
    private void updateBox(Rectangle r) {
        left = left > r.x ? r.x : left;
        right = right < r.x + r.width ? r.x + r.width : right;
        top = top > r.y ? r.y : top;
        bottom = bottom < r.y + r.height ? r.y + r.height : bottom;
    }

    /**
     * Getter for bounding box.
     *
     * @return Bounding box
     */
    public Rectangle getBoundingBox() {
        if (left == Integer.MAX_VALUE) {
            left = 0;
        }

        if (top == Integer.MAX_VALUE) {
            top = 0;
        }

        return new Rectangle(left, top, right - left, bottom - top);

    }
}
