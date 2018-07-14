package hr.fer.zemris.java.hw16.jvdraw.editors;

import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.tools.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FilledPolygonEditor extends GeometricalObjectEditor {

    private FilledPolygon polygon;
    private List<JSpinner> xComponents;
    private List<JSpinner> yComponents;

    private JSpinner fcolorR;

    /**
     * JSpinner for red foreground color.
     */
    private JSpinner fcolorG;

    /**
     * JSpinner for red foreground color.
     */
    private JSpinner fcolorB;

    /**
     * JSpinner for red background color.
     */
    private JSpinner bcolorR;

    /**
     * JSpinner for red background color.
     */
    private JSpinner bcolorG;

    /**
     * JSpinner for red background color.
     */
    private JSpinner bcolorB;

    public FilledPolygonEditor(FilledPolygon filledPolygon) {
        super();
        this.polygon=filledPolygon;
        xComponents = new ArrayList<>();
        yComponents= new ArrayList<>();
        int pointsSize = filledPolygon.getPoints().size();

        List<Point> points = filledPolygon.getPoints();


        this.setLayout(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(pointsSize+2, 1));
        JPanel right = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.add(left, BorderLayout.WEST);
        this.add(right, BorderLayout.EAST);

        for (int i=0;i<pointsSize;i++){
            left.add(new JLabel("Point"+i+1+" (x,y)"));
        }
        left.add(new JLabel("Foregorund Color (r,g,b): "));
        left.add(new JLabel("Background Color (r,g,b): "));
        c.fill = GridBagConstraints.HORIZONTAL;

        for (int i=0;i<pointsSize;i++){
            c.gridx=0;
            c.gridy=i;
            JSpinner xSpiner = new JSpinner(new SpinnerNumberModel(points.get(i).x,0,2000,1));
            xComponents.add(xSpiner);
            right.add(xSpiner,c);

            c.gridx=1;
            c.gridy=i;
            JSpinner ySpiner = new JSpinner(new SpinnerNumberModel(points.get(i).y,0,2000,1));
            yComponents.add(ySpiner);
            right.add(ySpiner,c);
        }

        c.gridx = 0;
        c.gridy = pointsSize;
        fcolorR = new JSpinner(new SpinnerNumberModel(polygon.getFgColor().getRed(), 0, 255, 1));
        right.add(fcolorR, c);

        c.gridx = 1;
        c.gridy = pointsSize;
        fcolorG = new JSpinner(new SpinnerNumberModel(polygon.getFgColor().getGreen(), 0, 255, 1));
        right.add(fcolorG, c);

        c.gridx = 2;
        c.gridy = pointsSize;
        fcolorB = new JSpinner(new SpinnerNumberModel(polygon.getFgColor().getBlue(), 0, 255, 1));
        right.add(fcolorB, c);

        c.gridx = 0;
        c.gridy = pointsSize+1;
        bcolorR = new JSpinner(new SpinnerNumberModel(polygon.getBgColor().getRed(), 0, 255, 1));
        right.add(bcolorR, c);

        c.gridx = 1;
        c.gridy = pointsSize+1;
        bcolorG = new JSpinner(new SpinnerNumberModel(polygon.getBgColor().getGreen(), 0, 255, 1));
        right.add(bcolorG, c);

        c.gridx = 2;
        c.gridy = pointsSize+1;
        bcolorB = new JSpinner(new SpinnerNumberModel(polygon.getBgColor().getBlue(), 0, 255, 1));
        right.add(bcolorB, c);

    }

    @Override
    public void checkEditing() {
        List<Point> points = getPoints(xComponents,yComponents);


        if (points.size()>=4){
            if (!checkConvec(points)){
                throw new RuntimeException("Nije konveksan!");
            }
        }

        if (checkClose(points)){
            throw new RuntimeException("Točke su preblizu!");
        }

    }

    private boolean checkClose(List<Point> points) {
        for (int i=0;i<points.size()-1;i++){
            if (closeEnough(points.get(0),points.get(1))){
                return true;
            }
        }
        return false;
    }


    private boolean closeEnough(Point p1, Point p2){
        return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y))<3;
    }

    private List<Point> getPoints(List<JSpinner> xComponents, List<JSpinner> yComponents) {
        List<Point> points = new ArrayList<>();

        for (int i=0;i<xComponents.size();i++){
            Point point = new Point((Integer)xComponents.get(i).getValue(),(Integer)yComponents.get(i).getValue());
            points.add(point);
        }

        return points;
    }

    @Override
    public void acceptEditing() {
        polygon.setPoints(getPoints(xComponents,yComponents));
        polygon.setFgColor(new Color((Integer) fcolorR.getValue(), (Integer) fcolorG.getValue(), (Integer) fcolorB
                .getValue
                        ()));
        polygon.setBgColor(new Color((Integer) bcolorR.getValue(), (Integer) bcolorG.getValue(), (Integer) bcolorB
                .getValue
                        ()));


    }

    private boolean checkConvec(List<Point> points){
        Point p0 =points.get(0);
        Point p1 = points.get(1);
        Point p2 = points.get(2);
        Vector2D v1 = new Vector2D(p0.x,p0.y);
        Vector2D v2 = new Vector2D(p1.x,p1.y);
        Vector2D v3 = new Vector2D(p2.x,p2.y);

        Vector2D v12 = v2.sub(v1);
        Vector2D v13 = v3.sub(v1);

        double z = v12.zComponentOfProduct(v13);

        boolean positive = z>0;

        for (int i=1;i<points.size();i++){
            p0 = points.get(i);
            if (i==points.size()-1){
                p1 = points.get(0);
            } else {
                p1 = points.get(i+1);
            }
            if (i==points.size()-2){
                p2 = points.get(0);
            } else if (i==points.size()-1){
                p2=points.get(1);
            } else {
                p2= points.get(i+2);
            }

            v1 = new Vector2D(p0.x,p0.y);
            v2 = new Vector2D(p1.x,p1.y);
            v3 = new Vector2D(p2.x,p2.y);

            v12 = v2.sub(v1);
            v13 = v3.sub(v1);

            double z1 = v12.zComponentOfProduct(v13);

            if (z1==0){
                continue;
            }

            if (z1>0 && !positive){
                return false;
            }

            if (z1<0 && positive){
                return false;
            }
        }

        return true;

    }



}
