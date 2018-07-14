package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents trigonometric servlet. It is used for displaying angles and its sinus and kosinus values.
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String a = req.getParameter("a");
        String b = req.getParameter("b");

        int aInt = 0;
        int bInt = 360;

        if (a != null) {
            try {
                aInt = Integer.parseInt(a);
            } catch (NumberFormatException ignorable) {

            }
        }

        if (b != null) {
            try {
                bInt = Integer.parseInt(b);
            } catch (NumberFormatException ignorable) {
            }
        }

        if (aInt > bInt) {
            int temp = bInt;
            bInt = aInt;
            aInt = temp;
        }

        if (bInt > (aInt + 720)) {
            bInt = aInt + 720;
        }

        List<Angle> angleData = new ArrayList<>();

        for (int i = aInt; i <= bInt; i++) {
            angleData.add(new Angle(i));
        }

        req.setAttribute("angleData", angleData);


        req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
    }


    /**
     * This class represents angle with its sinus and cosinus values.
     */
    public static class Angle {

        /**
         * Angle.
         */
        private int angle;

        /**
         * Sinus value.
         */
        private double sinus;

        /**
         * Kosinus value
         */
        private double kosinus;

        /**
         * Basic constructor.
         *
         * @param angle Angle
         */
        public Angle(int angle) {
            this.angle = angle;
            this.sinus = Math.sin(angle * Math.PI / 180);
            this.kosinus = Math.cos(angle * Math.PI / 180);
        }

        /**
         * Getter for angle.
         *
         * @return Angle
         */
        public int getAngle() {
            return angle;
        }

        /**
         * Getter for sinus.
         *
         * @return Sinus
         */
        public double getSinus() {
            return sinus;
        }

        /**
         * Getter for kosinus.
         *
         * @return Kosinus
         */
        public double getKosinus() {
            return kosinus;
        }
    }
}
