package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Song;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class represents pool servlet. It is used for displaying web application pool.
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pollId = req.getParameter("pollID");

        if (pollId != null) {
            try {
                long pollIdLong = Long.valueOf(pollId);
                List<Song> songs = DAOProvider.getDao().getSongs(pollIdLong);
                req.setAttribute("songs", songs);
                req.setAttribute("pollID", pollIdLong);
            } catch (NumberFormatException ignorable) {
            }

        }

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }


}
