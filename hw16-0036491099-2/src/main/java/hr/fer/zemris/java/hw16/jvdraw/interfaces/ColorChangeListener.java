package hr.fer.zemris.java.hw16.jvdraw.interfaces;

import java.awt.*;

/**
 * This interface represents color change listener. It is used for listening for changes in {@link IColorProvider}.
 */
public interface ColorChangeListener {

    /**
     * This method is called when new color is selected.
     *
     * @param source   Source of change
     * @param oldColor Old color
     * @param newColor New color
     */
    void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}