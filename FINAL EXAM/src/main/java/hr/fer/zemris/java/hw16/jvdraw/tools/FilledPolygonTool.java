package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.Tool;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPolygon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FilledPolygonTool implements Tool {

    private Graphics2D g2d;

    private IColorProvider fgColorProvider;

    private IColorProvider bgColorProvider;

    private DrawingModel drawingModel;

    private Point firstPoint;

    private Point lastPoint;

    private Point currentPoint;

    private FilledPolygon drawingFilledPolygon;

    private JVDraw jvDraw;

    public FilledPolygonTool(IColorProvider fgColorProvider, IColorProvider bgColorProvider, DrawingModel drawingModel, JVDraw jvDraw) {
        this.fgColorProvider = fgColorProvider;
        this.bgColorProvider = bgColorProvider;
        this.drawingModel = drawingModel;
        this.jvDraw = jvDraw;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)){
            drawingModel.remove(drawingFilledPolygon);
            drawingFilledPolygon=null;
            firstPoint=null;
            currentPoint=null;
            lastPoint=null;
            return;
        }

        if (firstPoint==null){
            firstPoint=e.getPoint();
            lastPoint=e.getPoint();
        } else {

            if (currentPoint==null){
                mouseMoved(e);
//                currentPoint=e.getPoint();
            }
            if (closeEnough(currentPoint,lastPoint)){
                if (drawingFilledPolygon.getPoints().size()==3) {
                    JOptionPane.showMessageDialog(jvDraw, "PREMALO TOČAKA!");
                    return;
                }
                drawingFilledPolygon.removeCurrentPoint();
                drawingFilledPolygon=null;
                firstPoint=null;
                currentPoint=null;
                lastPoint=null;
                return;
            }


            // PROVJERI KONVEKSNOST
            List<Point> points = new ArrayList<>(drawingFilledPolygon.getPoints());
            if (points.size()>=4){
                if (!checkConvec(points)){
                    JOptionPane.showMessageDialog(jvDraw, "NIJE KONVEKSAN!");
                    return;
                }
            }


            lastPoint=e.getPoint();
            currentPoint=null;
            drawingFilledPolygon.removeCurrentPoint();
            drawingFilledPolygon.addPoint(lastPoint);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (firstPoint!=null){
            if (currentPoint==null){
                currentPoint=e.getPoint();
                if (drawingFilledPolygon!=null) {
                    drawingFilledPolygon.addPoint(currentPoint);
                }
            } else {
                currentPoint = e.getPoint();
            }
            drawPolygon();
        }

    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void paint(Graphics2D g2d) {
        this.g2d=g2d;

    }


    private void drawPolygon() {
        if (drawingFilledPolygon==null){
            drawingFilledPolygon= new FilledPolygon(firstPoint,currentPoint,fgColorProvider.getCurrentColor(),
                    bgColorProvider.getCurrentColor());
            drawingModel.add(drawingFilledPolygon);
        } else {
            drawingFilledPolygon.setCurrentPoint(currentPoint);
        }
    }

    private boolean closeEnough(Point p1, Point p2){
        return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y))<3;
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
