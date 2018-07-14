package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.Tool;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This class represents implementation of {@link Tool} for drawing {@link FilledCircle}.
 */
public class FilledCircleTool implements Tool {

    /**
     * Graphics.
     */
    private Graphics2D g2d;

    /**
     * Foreground color provider.
     */
    private IColorProvider fgColorProvider;

    /**
     * Background color provider.
     */
    private IColorProvider bgColorProvider;

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
     * Filled circle to be drawn.
     */
    private FilledCircle drawingFilledCircle;

    /**
     * Basic constructor.
     *
     * @param fgColorProvider Foreground color provider
     * @param bgColorProvider Background color provider
     * @param drawingModel    Drawing model
     */
    public FilledCircleTool(IColorProvider fgColorProvider, IColorProvider bgColorProvider, DrawingModel drawingModel) {
        this.fgColorProvider = fgColorProvider;
        this.bgColorProvider = bgColorProvider;
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
            drawingModel.remove(drawingFilledCircle);
            drawingFilledCircle = null;
            drawingModel.add(new FilledCircle(startPoint, endPoint, fgColorProvider.getCurrentColor(), bgColorProvider
                    .getCurrentColor()));
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
        if (drawingFilledCircle == null) {
            drawingFilledCircle = new FilledCircle(startPoint, endPoint, fgColorProvider.getCurrentColor(),
                    bgColorProvider.getCurrentColor());
            drawingModel.add(drawingFilledCircle);
        } else {
            drawingFilledCircle.setEndPoint(endPoint);
            drawingFilledCircle.setFgColor(fgColorProvider.getCurrentColor());
            drawingFilledCircle.setBgColor(bgColorProvider.getCurrentColor());
        }
    }
}
