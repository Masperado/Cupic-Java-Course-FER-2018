package hr.fer.zemris.java.hw16.jvdraw.interfaces;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This interface represent tool used for drawing {@link hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject}
 * and adding them to {@link DrawingModel}.
 */
public interface Tool {

    /**
     * This method is called when mouse is pressed.
     *
     * @param e {@link MouseEvent}
     */
    void mousePressed(MouseEvent e);

    /**
     * This method is called when mouse is released.
     *
     * @param e {@link MouseEvent}
     */
    void mouseReleased(MouseEvent e);

    /**
     * This method is called when mouse is clicked.
     *
     * @param e {@link MouseEvent}
     */
    void mouseClicked(MouseEvent e);

    /**
     * This method is called when mouse is moved.
     *
     * @param e {@link MouseEvent}
     */
    void mouseMoved(MouseEvent e);

    /**
     * This method is called when mouse is dragged.
     *
     * @param e {@link MouseEvent}
     */
    void mouseDragged(MouseEvent e);

    /**
     * This method is used for painting object.
     *
     * @param g2d {@link Graphics2D}
     */
    void paint(Graphics2D g2d);

}