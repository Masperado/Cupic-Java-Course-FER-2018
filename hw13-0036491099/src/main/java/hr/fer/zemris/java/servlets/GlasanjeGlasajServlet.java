package hr.fer.zemris.java.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents voting servlet. It is used to vote in web application pool.
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

    @Override
    protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");

        if (id != null) {

            String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

            if (!Files.exists(Paths.get(fileName))) {
                Files.createFile(Paths.get(fileName));
            }

            List<String> lines = Files.readAllLines(Paths.get(fileName));

            List<Result> results = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split("\\t");
                int result = Integer.parseInt(parts[1]);
                if (id.equals(parts[0])) {
                    result++;
                }

                results.add(new Result(parts[0], null, null, String.valueOf(result)));
            }

            OutputStream os = Files.newOutputStream(Paths.get(fileName));
            StringBuilder sb = new StringBuilder();
            for (Result result : results) {
                sb.append(result.getId()).append("\t").append(result.getResult()).append("\n");
            }
            sb.setLength(sb.length() - 1);
            os.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        }

        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }

    /**
     * This class represents model for result of voting.
     */
    public static class Result {

        /**
         * Song votes.
         */
        private String result;

        /**
         * Result song.
         */
        private GlasanjeServlet.Song song;

        /**
         * Basic constructor.
         *
         * @param id     Song id
         * @param name   Song name
         * @param link   Song link
         * @param result Song result
         */
        public Result(String id, String name, String link, String result) {
            this.song = new GlasanjeServlet.Song(id, name, link);
            this.result = result;
        }

        /**
         * Getter for id.
         *
         * @return id
         */
        public String getId() {
            return song.getId();
        }

        /**
         * Getter for name.
         *
         * @return name
         */
        public String getName() {
            return song.getName();
        }

        /**
         * Getter for link.
         *
         * @return link
         */
        public String getLink() {
            return song.getLink();
        }

        /**
         * Getter for result.
         *
         * @return result
         */
        public String getResult() {
            return result;
        }
    }

}
