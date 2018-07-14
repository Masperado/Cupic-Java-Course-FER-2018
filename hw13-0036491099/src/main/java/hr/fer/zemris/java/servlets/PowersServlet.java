package hr.fer.zemris.java.servlets;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class represents power servlet. It is used to output powers of numbers into spreadsheet.
 */
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String aStr = req.getParameter("a");
        String bStr = req.getParameter("b");
        String nStr = req.getParameter("n");

        int a = 0;
        int b = 0;
        int n = 0;

        try {
            a = Integer.parseInt(aStr);
            b = Integer.parseInt(bStr);
            n = Integer.parseInt(nStr);
        } catch (NumberFormatException ex) {
            req.setAttribute("message", "Error in parsing parameters in powers servlet!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
            req.setAttribute("message", "Error in value of parameters in powers servlet!");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=powers.xls");

        Workbook powerBook = createPowerBook(a, b, n);

        powerBook.write(resp.getOutputStream());


    }

    /**
     * This method is used for creating spreadsheet.
     *
     * @param a From number
     * @param b To number
     * @param n Power
     * @return Spreadsheet
     */
    private Workbook createPowerBook(int a, int b, int n) {

        Workbook workbook = new HSSFWorkbook();

        for (int i = 1; i <= n; i++) {
            Sheet sheet = workbook.createSheet(i + "th power");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Number");
            row.createCell(1).setCellValue(i + "th power");
            for (int j = 1; j <= Math.abs(a - b) + 1; j++) {
                row = sheet.createRow(j);
                row.createCell(0).setCellValue(a + j - 1);
                row.createCell(1).setCellValue(Math.pow(a + j - 1, i));
            }
        }

        return workbook;


    }
}
