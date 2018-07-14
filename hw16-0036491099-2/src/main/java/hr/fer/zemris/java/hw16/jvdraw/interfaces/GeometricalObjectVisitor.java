package hr.fer.zemris.java.hw16.jvdraw.interfaces;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * This class represents visitor for {@link hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject}.
 */
public interface GeometricalObjectVisitor {

    /**
     * This method is called when {@link Line} is visited.
     *
     * @param line {@link Line}
     */
    void visit(Line line);

    /**
     * This method is called when {@link Circle} is visited.
     *
     * @param circle {@link Circle}
     */
    void visit(Circle circle);

    /**
     * This method is called when {@link FilledCircle} is visited.
     *
     * @param filledCircle {@link FilledCircle}
     */
    void visit(FilledCircle filledCircle);
}