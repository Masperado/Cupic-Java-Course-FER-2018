package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * This class is used for displaying fractals. Fractal definitions can be loaded from .txt file.
 */
public class Glavni3 {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        LSystemViewer.showLSystem(LSystemBuilderImpl::new);
    }
}
