package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.Tool;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This class represents implementation of {@link Tool} for drawing {@link Circle}.
 */
public class CircleTool implements Tool {

    /**
     * Graphics.
     */
    private Graphics2D g2d;

    /**
     * Foreground color provider.
     */
    private IColorProvider fgColorProvider;

    /**
     * Drawing model.
     */
    private DrawingModel drawingModel;

    /**
     * First click flag.
     */
    private boolean firstClick = true;

    /**
     * Start point.
     */
    private Point startPoint;

    /**
     * End point.
     */
    private Point endPoint;

    /**
     * Circle that will be drawn.
     */
    private Circle drawingCircle;

    /**
     * Basic constructor.
     *
     * @param fgColorProvider Foreground color provider
     * @param drawingModel    Drawing model
     */
    public CircleTool(IColorProvider fgColorProvider, DrawingModel drawingModel) {
        this.fgColorProvider = fgColorProvider;
        this.drawingModel = drawingModel;

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (firstClick) {
            startPoint = e.getPoint();
            firstClick = false;
        } else {
            endPoint = e.getPoint();
            drawingModel.remove(drawingCircle);
            drawingCircle = null;
            drawingModel.add(new Circle(startPoint, endPoint, fgColorProvider.getCurrentColor()));
            firstClick = true;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!firstClick) {
            endPoint = e.getPoint();
            drawCircle();
        }

    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void paint(Graphics2D g2d) {
        this.g2d = g2d;
    }

    /**
     * This method is used for drawing circle.
     */
    private void drawCircle() {
        if (drawingCircle == null) {
            drawingCircle = new Circle(startPoint, endPoint, fgColorProvider.getCurrentColor());
            drawingModel.add(drawingCircle);
        } else {
            drawingCircle.setEndPoint(endPoint);
            drawingCircle.setColor(fgColorProvider.getCurrentColor());
        }
    }

}
