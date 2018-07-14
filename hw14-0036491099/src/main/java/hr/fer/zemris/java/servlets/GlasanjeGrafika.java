package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Result;
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
import java.util.List;

/**
 * This servlet represents voting graphics servlet. It is used to display pie chart of voting results.
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafika extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("image/png");

        String pollId = req.getParameter("pollID");

        if (pollId != null) {
            try {
                long pollIdLong = Long.valueOf(pollId);
                List<Result> results = DAOProvider.getDao().getResults(pollIdLong);


                PieDataset dataset = createDataset(results);
                JFreeChart chart = createChart(dataset, "Rezultati");

                resp.getOutputStream().write(ChartUtils.encodeAsPNG(chart.createBufferedImage(500, 500)));
            } catch (NumberFormatException ignorable) {
            }

        }


    }


    /**
     * This method is used for creating pie dataset.
     *
     * @param results Results of voting
     */
    private PieDataset createDataset(List<Result> results) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (Result result : results) {
            dataset.setValue(result.getName(), Double.parseDouble(result.getResult()));
        }
        return dataset;

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
        plot.setStartAngle(289);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }

}