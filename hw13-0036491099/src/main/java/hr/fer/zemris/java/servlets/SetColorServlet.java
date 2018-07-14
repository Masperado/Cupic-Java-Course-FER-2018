package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class represents set color servlet. It is used for changing background color of web application.
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String color = req.getParameter("color");

        if (color != null) {
            req.getSession().setAttribute("pickedBgCol", color);
        }

        req.getRequestDispatcher("colors.jsp").forward(req, resp);
    }
}
