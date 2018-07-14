package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class represents voting servlet. It is used to vote in web application pool.
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String pollId = req.getParameter("pollID");

        if (id != null && pollId != null) {
            try {
                long idLong = Long.valueOf(id);
                long pollIdLong = Long.valueOf(pollId);
                DAOProvider.getDao().voteFor(pollIdLong, idLong);
            } catch (NumberFormatException ignorable) {
            }

        }

        resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollId);
    }

}
