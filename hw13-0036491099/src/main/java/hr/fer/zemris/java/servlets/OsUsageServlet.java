package hr.fer.zemris.java.servlets;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class represents os usage servlet. It is used for creating to display pie chart of os usage pool.
 */
@WebServlet("/osUsage")
public class OsUsageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("image/png");

        PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset, "OS Usage");

        resp.getOutputStream().write(ChartUtils.encodeAsPNG(chart.createBufferedImage(500, 500)));
    }


    /**
     * This method is used for creating pie dataset.
     */
    private PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 90);
        result.setValue("Mac", 2);
        result.setValue("Windows", 8);
        return result;

    }

    /**
     * This method is used for creating {@link JFreeChart}.
     *
     * @param dataset Dataset
     * @param title   Title of chart
     * @return {@link JFreeChart}
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
                title,                  // chart title
                dataset,                // data
                true,                   // include legend
                true,
                false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }

}