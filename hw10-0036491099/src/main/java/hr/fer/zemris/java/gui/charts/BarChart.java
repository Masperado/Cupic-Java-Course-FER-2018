package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class represents BarChart. It is used for representing bar charts.
 */
public class BarChart {

    /**
     * List of {@link XYValue}.
     */
    private List<XYValue> xyList;

    /**
     * Description of x axis.
     */
    private String xAxis;

    /**
     * Description of y axis.
     */
    private String yAxis;

    /**
     * Minimum y value.
     */
    private int miny;

    /**
     * Maximum y value.
     */
    private int maxy;

    /**
     * Difference between y values.
     */
    private int yDiff;

    /**
     * Basic constructor.
     *
     * @param xyList List of {@link XYValue}
     * @param xAxis  Description of x axis
     * @param yAxis  Description of y axis
     * @param miny   Minimum y value
     * @param maxy   Maximum y value
     * @param yDiff  Difference between y values
     */
    public BarChart(List<XYValue> xyList, String xAxis, String yAxis, int miny, int maxy, int yDiff) {
        this.xyList = xyList;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.miny = miny;
        this.maxy = maxy;
        this.yDiff = yDiff;
    }

    /**
     * Getter for list of {@link XYValue}.
     *
     * @return List of {@link XYValue}
     */
    public List<XYValue> getXyList() {
        return xyList;
    }

    /**
     * Getter for x axis description.
     *
     * @return X axis description
     */
    public String getxAxis() {
        return xAxis;
    }

    /**
     * Getter for y axis description.
     *
     * @return Y axis description
     */
    public String getyAxis() {
        return yAxis;
    }

    /**
     * Getter for miny.
     *
     * @return miny
     */
    public int getMiny() {
        return miny;
    }

    /**
     * Getter for maxy.
     *
     * @return maxy
     */
    public int getMaxy() {
        return maxy;
    }

    /**
     * Getter for ydiff.
     *
     * @return ydiff
     */
    public int getyDiff() {
        return yDiff;
    }
}
