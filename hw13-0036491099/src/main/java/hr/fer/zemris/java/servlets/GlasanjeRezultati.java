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
 * This class represents voting results server. It is used for displaying results of web application pool.
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultati extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<GlasanjeGlasajServlet.Result> results = getResults(req);

        List<GlasanjeGlasajServlet.Result> winners = new ArrayList<>();
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
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }

    /**
     * This method is used for getting results of pool.
     *
     * @param req {@link HttpServletRequest}
     * @return Sorted list of results
     * @throws IOException Input output exception
     */
    public static List<GlasanjeGlasajServlet.Result> getResults(HttpServletRequest req) throws IOException {
        String fileNameSongs = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        List<String> lines = Files.readAllLines(Paths.get(fileNameSongs));

        List<GlasanjeServlet.Song> songs = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\t");
            songs.add(new GlasanjeServlet.Song(parts[0], parts[1], parts[2]));
        }


        String fileNameResults = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");


        if (!Files.exists(Paths.get(fileNameResults))) {
            Files.createFile(Paths.get(fileNameResults));
        }

        lines = Files.readAllLines(Paths.get(fileNameResults));

        List<GlasanjeGlasajServlet.Result> results = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\t");
            int result = Integer.parseInt(parts[1]);
            String name = "";
            String link = "";
            for (GlasanjeServlet.Song song : songs) {
                if (song.getId().equals(parts[0])) {
                    name = song.getName();
                    link = song.getLink();
                }
            }
            results.add(new GlasanjeGlasajServlet.Result(parts[0], name, link, String.valueOf(result)));
        }


        results.sort((o1, o2) -> o2.getResult().compareTo(o1.getResult()));

        return results;
    }
}
