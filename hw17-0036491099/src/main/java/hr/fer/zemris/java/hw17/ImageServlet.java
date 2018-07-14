
package hr.fer.zemris.java.hw17;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class represents image servlet. It is used for displaying
 * images.
 *
 * @author Josip Torić
 */
@WebServlet(urlPatterns = {"/image"})
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("image/jpg");

        String name = req.getParameter("name");

        String thumb = req.getParameter("thumb");

        if (thumb == null) {
            thumb = "no";
        }

        if (name == null) {
            return;
        }

        Path path;

        if (thumb.equals("yes")) {
            path = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails"), name);
        } else {
            path = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike"), name);
        }

        BufferedImage bi = ImageIO.read(path.toFile());
        OutputStream out = resp.getOutputStream();
        ImageIO.write(bi, "png", out);
        out.close();

    }

}
