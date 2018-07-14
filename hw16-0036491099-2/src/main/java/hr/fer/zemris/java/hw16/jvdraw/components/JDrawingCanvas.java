package hr.fer.zemris.java.hw16.jvdraw.components;

import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.Tool;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * This class represents JDrawingCanvas. It is used for drawing {@link GeometricalObject}.
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

    /**
     * Drawing model.
     */
    private DrawingModel model;

    /**
     * Current selected tool for drawing.
     */
    private Tool currentTool;

    /**
     * Mouse adapter.
     */
    private MouseAdapter adapter;

    /**
     * Mouse motion listener.
     */
    private MouseMotionListener motion;

    /**
     * Width of drawing canvas in pixels.
     */
    private int width;

    /**
     * Basic constructors.
     *
     * @param model       Drawing model
     * @param currentTool Current selected tool
     * @param width       Width of canvas
     */
    public JDrawingCanvas(DrawingModel model, Tool currentTool, int width) {
        this.model = model;
        this.currentTool = currentTool;
        this.width = width;

        model.addDrawingModelListener(this);
        addListeners();
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(3));

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
        for (int i = 0; i < model.getSize(); i++) {
            GeometricalObject object = model.getObject(i);
            object.accept(painter);
        }
        currentTool.paint(g2d);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, 600);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        repaint();
    }

    /**
     * This method is used for adding mouse listeners.
     */
    private void addListeners() {
        adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentTool.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                currentTool.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentTool.mouseReleased(e);
            }

        };

        addMouseListener(adapter);

        motion = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentTool.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                currentTool.mouseMoved(e);
            }
        };

        addMouseMotionListener(motion);
    }

    /**
     * This method is used for removing mouse listeners.
     */
    private void removeListeners() {
        removeMouseListener(adapter);
        removeMouseMotionListener(motion);
    }

    /**
     * This method is used for setting current tool.
     *
     * @param currentTool New tool
     */
    public void setCurrentTool(Tool currentTool) {
        removeListeners();
        this.currentTool = currentTool;
        addListeners();
        repaint();
    }

    /**
     * This method is used for setting width of screen.
     *
     * @param width Width of screen
     */
    public void setWidth(int width) {
        this.width = width;
    }
}
