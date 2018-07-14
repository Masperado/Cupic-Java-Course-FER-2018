package hr.fer.zemris.java.hw16.jvdraw.interfaces;

import java.awt.*;

/**
 * This interface represents color provider.
 */
public interface IColorProvider {

    /**
     * Getter for current color.
     *
     * @return Current color
     */
    Color getCurrentColor();

    /**
     * This method is used for adding {@link ColorChangeListener} to provider.
     *
     * @param l {@link ColorChangeListener}
     */
    void addColorChangeListener(ColorChangeListener l);

    /**
     * This method is used for removing {@link ColorChangeListener} from provider.
     *
     * @param l {@link ColorChangeListener}
     */
    void removeColorChangeListener(ColorChangeListener l);
}
