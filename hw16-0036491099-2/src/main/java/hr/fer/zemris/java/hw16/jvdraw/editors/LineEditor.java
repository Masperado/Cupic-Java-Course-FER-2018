package hr.fer.zemris.java.hw16.jvdraw.editors;

import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents line editor. It is used for editing {@link Line}.
 */
public class LineEditor extends GeometricalObjectEditor {

    /**
     * JSpinner for x component of start.
     */
    private JSpinner startX;

    /**
     * JSpinner for y component of start.
     */
    private JSpinner startY;

    /**
     * JSpinner for x component of end.
     */
    private JSpinner endX;

    /**
     * JSpinner for y component of end.
     */
    private JSpinner endY;

    /**
     * JSpinner for red color component.
     */
    private JSpinner colorR;

    /**
     * JSpinner for green color component.
     */
    private JSpinner colorG;

    /**
     * JSpinner for blue color component.
     */
    private JSpinner colorB;

    /**
     * Line that will be edited.
     */
    private Line line;

    /**
     * Basic constructor.
     *
     * @param line Line
     */
    public LineEditor(Line line) {
        this.line = line;
        this.setLayout(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(3, 1));
        JPanel right = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.add(left, BorderLayout.WEST);
        this.add(right, BorderLayout.EAST);

        left.add(new JLabel("Start point (x,y): "));
        left.add(new JLabel("End point (x,y): "));
        left.add(new JLabel("Color (r,g,b): "));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        startX = new JSpinner(new SpinnerNumberModel(line.getStartPoint().x, 0, 2000, 1));
        right.add(startX, c);

        c.gridx = 1;
        c.gridy = 0;
        startY = new JSpinner(new SpinnerNumberModel(line.getStartPoint().y, 0, 2000, 1));
        right.add(startY, c);


        c.gridx = 0;
        c.gridy = 1;
        endX = new JSpinner(new SpinnerNumberModel(line.getEndPoint().x, 0, 2000, 1));
        right.add(endX, c);

        c.gridx = 1;
        c.gridy = 1;
        endY = new JSpinner(new SpinnerNumberModel(line.getEndPoint().y, 0, 2000, 1));
        right.add(endY, c);


        c.gridx = 0;
        c.gridy = 2;
        colorR = new JSpinner(new SpinnerNumberModel(line.getColor().getRed(), 0, 255, 1));
        right.add(colorR, c);

        c.gridx = 1;
        c.gridy = 2;
        colorG = new JSpinner(new SpinnerNumberModel(line.getColor().getGreen(), 0, 255, 1));
        right.add(colorG, c);

        c.gridx = 2;
        c.gridy = 2;
        colorB = new JSpinner(new SpinnerNumberModel(line.getColor().getBlue(), 0, 255, 1));
        right.add(colorB, c);
    }

    @Override
    public void checkEditing() {
        // Checking done by JSpinner
    }

    @Override
    public void acceptEditing() {
        line.setStartPoint(new Point((Integer) startX.getValue(), (Integer) startY.getValue()));
        line.setEndPoint(new Point((Integer) endX.getValue(), (Integer) endY.getValue()));
        line.setColor(new Color((Integer) colorR.getValue(), (Integer) colorG.getValue(), (Integer) colorB.getValue()));

    }
}