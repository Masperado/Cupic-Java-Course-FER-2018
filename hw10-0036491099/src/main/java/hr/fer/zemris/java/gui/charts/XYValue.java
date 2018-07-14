package hr.fer.zemris.java.gui.charts;

/**
 * This class represents xy values. It is just a basic pair of values.
 */
public class XYValue {

    /**
     * X value.
     */
    private int x;

    /**
     * Y value.
     */
    private int y;

    /**
     * Basic constructor.
     *
     * @param x X value
     * @param y Y value
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x value.
     *
     * @return X value
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y value.
     *
     * @return Y value
     */
    public int getY() {
        return y;
    }
}
