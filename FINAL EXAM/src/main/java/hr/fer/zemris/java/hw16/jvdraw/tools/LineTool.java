package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.Tool;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This class represents implementation of {@link Tool} for drawing {@link Line}.
 */
public class LineTool implements Tool {

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
     * Line that will be drawn.
     */
    private Line drawingLine;

    /**
     * Basic constructor.
     *
     * @param fgColorProvider Foreground color provider
     * @param drawingModel    Drawing model
     */
    public LineTool(IColorProvider fgColorProvider, DrawingModel drawingModel) {
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
            drawingModel.remove(drawingLine);
            drawingLine = null;
            endPoint = e.getPoint();
            firstClick = true;
            drawingModel.add(new Line(startPoint, endPoint, fgColorProvider.getCurrentColor()));
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!firstClick) {
            endPoint = e.getPoint();
            drawLine();
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
     * This method is used for drawing line.
     */
    private void drawLine() {
        if (drawingLine == null) {
            drawingLine = new Line(startPoint, endPoint, fgColorProvider.getCurrentColor());
            drawingModel.add(drawingLine);
        } else {
            drawingLine.setEndPoint(endPoint);
            drawingLine.setColor(fgColorProvider.getCurrentColor());
        }
    }

}
