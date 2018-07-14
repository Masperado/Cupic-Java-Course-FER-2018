package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.components.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.*;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/crtaj")
public class getPngServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            // PARSIRAJ ZAHTJEV
            DrawingModel drawingModel = new DrawingModelImpl();

            System.out.println("tu sam");

            req.setCharacterEncoding("UTF-8");
            String data = req.getParameter("text").toString();

            System.out.println(data);

            /**
             * OVO JE NAJBITNIJA LINIJA KOJU SI NAPISA U ŽIVOTU
             */
            String[] lines = data.split("\r\n");


            List<GeometricalObject> objects = new ArrayList<>();
            for (String line : lines) {
                if (line.startsWith("LINE")) {
                    objects.add(Line.parse(line));
                } else if (line.startsWith("CIRCLE")) {
                    objects.add(Circle.parse(line));
                } else if (line.startsWith("FCIRCLE")) {
                    objects.add(FilledCircle.parse(line));
                } else if (line.startsWith("FPOLY")) {
                    objects.add(FilledPolygon.parse(line));
                } else {
                    throw new RuntimeException();
                }
            }
            for (GeometricalObject object : objects) {
                drawingModel.add(object);
            }

            GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
            for (int i = 0; i < drawingModel.getSize(); i++) {
                GeometricalObject object = drawingModel.getObject(i);
                object.accept(bbcalc);
            }
            Rectangle box = bbcalc.getBoundingBox();
            BufferedImage image = new BufferedImage(
                    box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
            );
            Graphics2D g = image.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, box.width, box.height);
            g.translate(-box.x, -box.y);
            GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
            for (int i = 0; i < drawingModel.getSize(); i++) {
                GeometricalObject object = drawingModel.getObject(i);
                object.accept(painter);
            }
            g.dispose();

            ImageIO.write(image, "png", resp.getOutputStream());

        } catch (Exception e){
            req.getRequestDispatcher("WEB-INF/error.html").forward(req,resp);
        }

    }
}
