package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents voting results server. It is used for displaying results of web application pool.
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultati extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pollId = req.getParameter("pollID");

        if (pollId != null) {
            try {
                long pollIdLong = Long.valueOf(pollId);
                List<Result> results = DAOProvider.getDao().getResults(pollIdLong);

                List<Result> winners = new ArrayList<>();
                winners.add(results.get(0));
                for (int i = 1; i < results.size(); i++) {
                    if (winners.get(0).getResult().equals(results.get(i).getResult())) {
                        winners.add(results.get(i));
                    } else {
                        break;
                    }
                }

                req.setAttribute("results", results);
                req.setAttribute("winners", winners);
                req.setAttribute("pollID", pollIdLong);
            } catch (NumberFormatException ignorable) {
            }

        }

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
