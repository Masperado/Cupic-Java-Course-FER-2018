package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * This class is used for displaying Koch Curve.
 */
public class Glavni2 {

    /**
     * Main method.
     *
     * @param args Command line argument
     */
    public static void main(String[] args) {
        LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
    }

    /**
     * This method is used for creating koch curve.
     *
     * @param provider Provider for building koch curve.
     * @return Koch curve system
     */
    private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
        String[] data = new String[]{
                "origin 0.05 0.4",
                "angle 0",
                "unitLength 0.9",
                "unitLengthDegreeScaler 1.0 / 3.0",
                "",
                "command F draw 1",
                "command + rotate 60",
                "command - rotate -60",
                "",
                "axiom F",
                "",
                "production F F+F--F+F"
        };
        return provider.createLSystemBuilder().configureFromText(data).build();
    }
}
