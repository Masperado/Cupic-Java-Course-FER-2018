package hr.fer.zemris.java.hw16.jvdraw.components;

import hr.fer.zemris.java.hw16.jvdraw.interfaces.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.IColorProvider;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents JColorLabel. It is used for displaying information about current foreground and background
 * color.
 */
public class JColorLabel extends JLabel implements ColorChangeListener {

    /**
     * Foreground color provider.
     */
    private IColorProvider fgColorProvider;

    /**
     * Background color provider.
     */
    private IColorProvider bgColorProvider;

    /**
     * Basic constructor.
     *
     * @param fgColorProvider Foreground color provider
     * @param bgColorProvider Background color provider
     */
    public JColorLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        this.fgColorProvider = fgColorProvider;
        this.bgColorProvider = bgColorProvider;

        fgColorProvider.addColorChangeListener(this);
        bgColorProvider.addColorChangeListener(this);

        setText("Foregorund color: " + printColor(fgColorProvider.getCurrentColor()) + ", background color: " +
                "" + printColor(bgColorProvider
                .getCurrentColor()) + ".");
    }


    @Override
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
        setText("Foregorund color: " + printColor(fgColorProvider.getCurrentColor()) + ", background color: " +
                "" + printColor(bgColorProvider
                .getCurrentColor()) + ".");
    }

    /**
     * This method is used for returning string representation of color.
     *
     * @param color Color
     * @return String representation
     */
    private String printColor(Color color) {
        return "(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
    }
}
