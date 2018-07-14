package hr.fer.zemris.java.gui.charts;

import javax.swing.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.List;

/**
 * This class represents BarChart component. It is used for displaying {@link BarChart}.
 */
public class BarChartComponent extends JComponent {

    /**
     * {@link BarChart} that will be displayed.
     */
    private BarChart chart;

    /**
     * Basic constructor.
     *
     * @param chart {@link BarChart} that will be displayed.
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;
    }


    @Override
    public void paintComponent(Graphics g) {

        // Cast to graphics2D
        Graphics2D g2d = (Graphics2D) g;

        // Save previous g2d values
        Stroke prevStroke = g2d.getStroke();
        Font prevFont = g2d.getFont();
        Color prevColor = g2d.getColor();

        // Get font for numbers
        Font numberFont = new Font(prevFont.getName(), Font.BOLD, prevFont.getSize() + 8);
        g2d.setFont(numberFont);

        // Get dimension, insets and fontmetrics
        Dimension dim = getSize();
        Insets ins = getInsets();
        FontMetrics fm = g2d.getFontMetrics();
        int yNumberLength = fm.stringWidth(Integer.toString(chart.getMaxy()));
        List<XYValue> values = chart.getXyList();

        int space = 20;

        int dashLength = 6;

        // Calculate origin, and end of x axis and end of y axis
        int startX = ins.left + space + fm.getHeight() + space + yNumberLength + space / 2 + dashLength;
        int startY = dim.height - ins.bottom - space - fm.getHeight() - space - fm.getHeight() - space / 2 - dashLength;
        int endOfXAxis = dim.width - ins.right - space;
        int endOfYAxis = ins.top + space;

        // Calculate column and row width and number of values
        int columnWidth = (endOfXAxis - startX) / values.size();
        int countOfValues = (chart.getMaxy() - chart.getMiny()) / chart.getyDiff();
        if ((chart.getMaxy() - chart.getMiny()) % chart.getyDiff() != 0) {
            countOfValues++;
        }
        int rowWidth = (startY - endOfYAxis) / countOfValues;

        // Draw dashes on y axis, values on y axis and grid on y axis
        for (int i = chart.getMiny(); i <= chart.getMiny() + countOfValues; i++) {
            // Dashes
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.GRAY);
            g2d.drawLine(startX - dashLength, startY - (i - chart.getMiny()) * rowWidth, startX,
                    startY - (i - chart.getMiny()) * rowWidth);

            // Values
            g2d.setColor(Color.BLACK);
            g2d.setFont(numberFont);
            String yValue = Integer.toString(i * chart.getyDiff() - chart.getMiny());
            g2d.drawString(yValue, startX - space / 2 - dashLength - fm.stringWidth(yValue),
                    startY - (i - chart.getMiny()) * rowWidth + fm.getHeight() / 3);

            // Grid
            if (i != chart.getMiny()) {
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.RED.brighter());
                g2d.drawLine(startX, startY - (i - chart.getMiny()) * rowWidth, endOfXAxis,
                        startY - (i - chart.getMiny()) * rowWidth);
            }
        }

        // Draw dashes on x axis, values on x axis, grid on x axis, rectangles
        // representing values and their shadows
        for (int i = 0; i <= values.size(); i++) {

            // Dashes
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.GRAY);
            g2d.drawLine(startX + i * columnWidth, startY + dashLength, startX + i * columnWidth, startY);

            // Grid
            if (i != 0) {
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.RED);
                g2d.drawLine(startX + i * columnWidth, startY, startX + i * columnWidth, endOfYAxis);
            }

            if (i < values.size()) {
                // Values
                g2d.setColor(Color.BLACK);
                g2d.setFont(numberFont);
                g2d.drawString(Integer.toString(values.get(i).getX()), startX + i * columnWidth + columnWidth / 2,
                        startY + space / 2 + fm.getHeight() + dashLength);

                // Rectangle
                g2d.setColor(Color.RED);
                int yValue = values.get(i).getY() > chart.getMaxy() ? chart.getMaxy() : values.get(i).getY();
                g2d.fillRect(startX + i * columnWidth,
                        startY - (yValue - chart.getMiny()) * rowWidth / chart.getyDiff(), columnWidth,
                        (yValue - chart.getMiny()) * rowWidth / chart.getyDiff() - 1);

                // Separation between rectangles
                if (i > 0) {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setColor(Color.WHITE);
                    g2d.drawLine(startX + i * columnWidth + 1, startY, startX + i * columnWidth + 1,
                            startY - values.get(i - 1).getY() * rowWidth / chart.getyDiff());
                }

                // Shadow
                g2d.setColor(Color.BLACK);
                g2d.fillRect(startX + (i + 1) * columnWidth,
                        startY - (yValue - chart.getMiny()) * rowWidth / chart.getyDiff(), 5,
                        (yValue - chart.getMiny()) * rowWidth / chart.getyDiff() - 1);

            }

        }

        // Draw axes
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.GRAY);
        g2d.drawLine(startX - dashLength, startY, endOfXAxis, startY);
        g2d.drawLine(startX, startY + dashLength, startX, endOfYAxis);

        // Draw arrows at end of axes
        for (int i = -dashLength / 2; i <= dashLength / 2; i++) {
            g2d.drawLine(endOfXAxis, startY + i, endOfXAxis + dashLength + 1, startY);
            g2d.drawLine(startX + i, endOfYAxis, startX, endOfYAxis - dashLength - 1);
        }

        // Draw x axis description
        g2d.setFont(prevFont);
        g2d.setColor(Color.BLACK);
        g2d.drawString(chart.getxAxis(), (endOfXAxis + startX) / 2 - fm.stringWidth(chart.getxAxis()) / 4,
                startY + dashLength + space / 2 + fm.getHeight() + space);

        // Draw y axis description
        AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
        g2d.setTransform(at);
        g2d.drawString(chart.getyAxis(), (endOfYAxis - startY) / 2 - fm.stringWidth(chart.getyAxis()) / 2,
                startX - dashLength - space / 2 - yNumberLength - space);

        // Reset graphics to initial state
        at = AffineTransform.getQuadrantRotateInstance(0);
        g2d.setTransform(at);
        g2d.setColor(prevColor);
        g2d.setFont(prevFont);
        g2d.setStroke(prevStroke);

    }


}
