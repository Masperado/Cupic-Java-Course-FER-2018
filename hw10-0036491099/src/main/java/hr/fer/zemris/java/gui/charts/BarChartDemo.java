package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for displaying {@link BarChart}. It gets description of bar chart through file which must be
 * given through command line.
 */
public class BarChartDemo extends JFrame {

    /**
     * {@link BarChart} that will be displayed.
     */
    private BarChart chart;

    /**
     * Basic constructor.
     *
     * @param chart {@link BarChart} that will be displayed.
     */
    public BarChartDemo(BarChart chart) {

        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BarChart");
        setLocation(20, 20);
        setSize(1000, 1000);
        this.chart = chart;
        initGUI();
    }

    /**
     * This class is used for initializing graphical user interface.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.add(new BarChartComponent(chart));
        cp.setBackground(Color.WHITE);
    }


    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Neispravan broj argumenata");
            System.exit(1);
        }

        BarChart chart = null;
        try {
            chart = parseChart(Files.readAllLines(Paths.get(args[0])));
        } catch (IOException | NumberFormatException e) {
            System.out.println("Neispravna datoteka!");
            System.exit(1);
        }

        final BarChart finalChart = chart;

        SwingUtilities.invokeLater(() -> {
            BarChartDemo prozor = new BarChartDemo(finalChart);
            prozor.setVisible(true);
        });
    }

    /**
     * This method is used for parsing List of String to {@link BarChart}.
     *
     * @param lines List of strings
     * @return {@link BarChart}
     * @throws IOException If List of Strings are invalid
     */
    private static BarChart parseChart(List<String> lines) throws IOException {
        if (lines.size() != 6) {
            throw new IOException();
        }

        List<XYValue> values = parseValues(lines.get(2));
        return new BarChart(values, lines.get(0), lines.get(1), Integer.valueOf(lines.get(3)),
                Integer.valueOf(lines.get(4)), Integer.valueOf(lines.get(5)));

    }

    /**
     * This method is used for parsing String into List of {@link XYValue}.
     *
     * @param string String
     * @return List of {@link XYValue}
     * @throws IOException If String is invalid
     */
    private static List<XYValue> parseValues(String string) throws IOException {
        String[] values = string.split(" ");
        List<XYValue> xyValues = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            String[] xy = values[i].split(",");
            if (xy.length != 2) {
                throw new IOException();
            }
            xyValues.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
        }
        return xyValues;

    }


}
