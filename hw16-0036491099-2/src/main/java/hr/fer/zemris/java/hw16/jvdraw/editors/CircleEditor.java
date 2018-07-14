package hr.fer.zemris.java.hw16.jvdraw.editors;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents circle editor. It is used for editing {@link Circle}.
 */
public class CircleEditor extends GeometricalObjectEditor {

    /**
     * JSpinner for x component of center.
     */
    private JSpinner centerX;

    /**
     * JSpinner for y component of center.
     */
    private JSpinner centerY;

    /**
     * JSpinner for radius.
     */
    private JSpinner radius;

    /**
     * JSpinner for red color.
     */
    private JSpinner colorR;

    /**
     * JSpinner for green color.
     */
    private JSpinner colorG;

    /**
     * JSpinner for blue color.
     */
    private JSpinner colorB;

    /**
     * Circle for editing.
     */
    private Circle circle;

    /**
     * Basic constructor.
     *
     * @param circle Circle
     */
    public CircleEditor(Circle circle) {
        this.circle = circle;

        this.setLayout(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(3, 1));
        JPanel right = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.add(left, BorderLayout.WEST);
        this.add(right, BorderLayout.EAST);

        left.add(new JLabel("Center (x,y): "));
        left.add(new JLabel("Radius: "));
        left.add(new JLabel("Color (r,g,b): "));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        centerX = new JSpinner(new SpinnerNumberModel(circle.getCenter().x, 0, 2000, 1));
        right.add(centerX, c);

        c.gridx = 1;
        c.gridy = 0;
        centerY = new JSpinner(new SpinnerNumberModel(circle.getCenter().y, 0, 2000, 1));
        right.add(centerY, c);

        c.gridx = 0;
        c.gridy = 1;
        radius = new JSpinner(new SpinnerNumberModel(circle.getRadius(), 1, 1000, 1));
        right.add(radius, c);

        c.gridx = 0;
        c.gridy = 2;
        colorR = new JSpinner(new SpinnerNumberModel(circle.getColor().getRed(), 0, 255, 1));
        right.add(colorR, c);

        c.gridx = 1;
        c.gridy = 2;
        colorG = new JSpinner(new SpinnerNumberModel(circle.getColor().getGreen(), 0, 255, 1));
        right.add(colorG, c);

        c.gridx = 2;
        c.gridy = 2;
        colorB = new JSpinner(new SpinnerNumberModel(circle.getColor().getBlue(), 0, 255, 1));
        right.add(colorB, c);
    }

    @Override
    public void checkEditing() {
        // Checking done by JSpinner
    }

    @Override
    public void acceptEditing() {
        circle.setCenter(new Point((Integer) centerX.getValue(), (Integer) centerY.getValue()));
        circle.setRadius((Integer) radius.getValue());
        circle.setColor(new Color((Integer) colorR.getValue(), (Integer) colorG.getValue(), (Integer) colorB.getValue
                ()));
    }
}
