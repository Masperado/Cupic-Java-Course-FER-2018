package hr.fer.zemris.java.hw16.jvdraw.visitors;

import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

import java.awt.*;

/**
 * This class represents painter for {@link hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject}. It is used for
 * drawing {@link hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject} to {@link Graphics2D}.
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

    /**
     * Graphics.
     */
    private Graphics2D g2d;

    /**
     * Basic constructor.
     *
     * @param g2d Graphics
     */
    public GeometricalObjectPainter(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void visit(Line line) {
        g2d.setColor(line.getColor());
        g2d.drawLine(line.getStartPoint().x, line.getStartPoint().y, line.getEndPoint().x, line.getEndPoint().y);
    }

    @Override
    public void visit(Circle circle) {
        g2d.setColor(circle.getColor());
        g2d.drawOval(circle.getCenter().x - circle.getRadius(), circle.getCenter().y - circle.getRadius(), circle.getRadius
                () * 2, circle
                .getRadius() * 2);

    }

    @Override
    public void visit(FilledCircle filledCircle) {
        g2d.setColor(filledCircle.getFgColor());
        g2d.drawOval(filledCircle.getCenter().x - filledCircle.getRadius(), filledCircle.getCenter().y - filledCircle.getRadius(), filledCircle.getRadius() * 2, filledCircle.getRadius() * 2);
        g2d.setColor(filledCircle.getBgColor());
        g2d.fillOval(filledCircle.getCenter().x - filledCircle.getRadius(), filledCircle.getCenter().y - filledCircle
                .getRadius(), filledCircle.getRadius() * 2, filledCircle.getRadius() * 2);
    }


}
