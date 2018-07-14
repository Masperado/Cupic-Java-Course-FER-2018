package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Result;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class represents voting spreadsheet servlet. It is used for creating spreadsheet of voting results.
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=rezultati.xls");

        String pollId = req.getParameter("pollID");

        if (pollId != null) {
            try {
                long pollIdLong = Long.valueOf(pollId);
                List<Result> results = DAOProvider.getDao().getResults(pollIdLong);

                Workbook powerBook = createResultsBook(results);
                powerBook.write(resp.getOutputStream());
            } catch (NumberFormatException ignorable) {
            }

        }


    }

    /**
     * This method is used for creating results spreadsheet.
     *
     * @param results Results of voting
     * @return Spreadsheet
     */
    private Workbook createResultsBook(List<Result> results) {

        Workbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet("Rezultati");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Pjesma");
        row.createCell(1).setCellValue("Broj glasova");
        int rowNumber = 1;
        for (Result result : results) {
            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(result.getName());
            row.createCell(1).setCellValue(result.getResult());
        }

        return workbook;


    }
}
