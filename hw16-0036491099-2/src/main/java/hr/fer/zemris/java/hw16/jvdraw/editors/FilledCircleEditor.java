package hr.fer.zemris.java.hw16.jvdraw.editors;

import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents filled circle editor. It is used for editing {@link FilledCircle}.
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

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
     * JSpinner for red foreground color.
     */
    private JSpinner fcolorR;

    /**
     * JSpinner for red foreground color.
     */
    private JSpinner fcolorG;

    /**
     * JSpinner for red foreground color.
     */
    private JSpinner fcolorB;

    /**
     * JSpinner for red background color.
     */
    private JSpinner bcolorR;

    /**
     * JSpinner for red background color.
     */
    private JSpinner bcolorG;

    /**
     * JSpinner for red background color.
     */
    private JSpinner bcolorB;

    /**
     * Filled circle for editing.
     */
    private FilledCircle circle;

    /**
     * Basic constructor.
     *
     * @param circle Filled circle
     */
    public FilledCircleEditor(FilledCircle circle) {
        this.circle = circle;

        this.setLayout(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(4, 1));
        JPanel right = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.add(left, BorderLayout.WEST);
        this.add(right, BorderLayout.EAST);

        left.add(new JLabel("Center (x,y): "));
        left.add(new JLabel("Radius: "));
        left.add(new JLabel("Foregorund Color (r,g,b): "));
        left.add(new JLabel("Background Color (r,g,b): "));

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
        fcolorR = new JSpinner(new SpinnerNumberModel(circle.getFgColor().getRed(), 0, 255, 1));
        right.add(fcolorR, c);

        c.gridx = 1;
        c.gridy = 2;
        fcolorG = new JSpinner(new SpinnerNumberModel(circle.getFgColor().getGreen(), 0, 255, 1));
        right.add(fcolorG, c);

        c.gridx = 2;
        c.gridy = 2;
        fcolorB = new JSpinner(new SpinnerNumberModel(circle.getFgColor().getBlue(), 0, 255, 1));
        right.add(fcolorB, c);

        c.gridx = 0;
        c.gridy = 3;
        bcolorR = new JSpinner(new SpinnerNumberModel(circle.getBgColor().getRed(), 0, 255, 1));
        right.add(bcolorR, c);

        c.gridx = 1;
        c.gridy = 3;
        bcolorG = new JSpinner(new SpinnerNumberModel(circle.getBgColor().getGreen(), 0, 255, 1));
        right.add(bcolorG, c);

        c.gridx = 2;
        c.gridy = 3;
        bcolorB = new JSpinner(new SpinnerNumberModel(circle.getBgColor().getBlue(), 0, 255, 1));
        right.add(bcolorB, c);

    }

    @Override
    public void checkEditing() {
        // Checking done by JSpinner
    }

    @Override
    public void acceptEditing() {
        circle.setCenter(new Point((Integer) centerX.getValue(), (Integer) centerY.getValue()));
        circle.setRadius((Integer) radius.getValue());
        circle.setFgColor(new Color((Integer) fcolorR.getValue(), (Integer) fcolorG.getValue(), (Integer) fcolorB
                .getValue
                        ()));
        circle.setBgColor(new Color((Integer) bcolorR.getValue(), (Integer) bcolorG.getValue(), (Integer) bcolorB
                .getValue
                        ()));

    }
}
