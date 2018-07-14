package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents pool servlet. It is used for displaying web application pool.
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        List<Song> songs = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\t");
            songs.add(new Song(parts[0], parts[1], parts[2]));
        }

        req.setAttribute("songs", songs);
        req.getRequestDispatcher("WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }

    /**
     * This class represents song used in web application pool.
     */
    public static class Song {

        /**
         * Song id.
         */
        private String id;

        /**
         * Song name.
         */
        private String name;

        /**
         * Song link.
         */
        private String link;

        /**
         * Basic constructor.
         *
         * @param id   Song id
         * @param name Song name
         * @param link Song link
         */
        public Song(String id, String name, String link) {
            this.id = id;
            this.name = name;
            this.link = link;
        }

        /**
         * Getter for id.
         *
         * @return Id
         */
        public String getId() {
            return id;
        }

        /**
         * Getter for name.
         *
         * @return Name
         */
        public String getName() {
            return name;
        }

        /**
         * Getter for link.
         *
         * @return Link
         */
        public String getLink() {
            return link;
        }
    }
}
