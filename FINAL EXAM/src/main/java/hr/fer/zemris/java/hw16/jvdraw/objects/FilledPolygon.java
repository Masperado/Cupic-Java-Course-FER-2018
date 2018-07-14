package hr.fer.zemris.java.hw16.jvdraw.objects;

import hr.fer.zemris.java.hw16.jvdraw.editors.FilledPolygonEditor;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.interfaces.GeometricalObjectVisitor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FilledPolygon extends GeometricalObject {

    private List<GeometricalObjectListener> listeners = new ArrayList<>();


    private List<Point> points = new ArrayList<>();

    private Color fgColor;

    private Color bgColor;

    public FilledPolygon(Point p0, Point p1, Color fgColor, Color bgColor) {
        this.fgColor = fgColor;
        this.bgColor = bgColor;
        points.add(p0);
        points.add(p1);
    }

    public FilledPolygon(List<Point> points, Color fgColor, Color bgColor) {
        this.fgColor = fgColor;
        this.bgColor = bgColor;
        this.points=points;
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new FilledPolygonEditor(this);
    }

    @Override
    public void addGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.add(l);
    }

    @Override
    public void removeGeometricalObjectListener(GeometricalObjectListener l) {
        listeners.remove(l);
    }

    public void addPoint(Point p){
        points.add(p);
        notifyListeners();
    }

    private void notifyListeners() {
        for (GeometricalObjectListener l : listeners) {
            l.geometricalObjectChanged(this);
        }
    }

    public void setCurrentPoint(Point p){
        points.remove(points.size()-1);
        points.add(p);
    }

    public void removeCurrentPoint(){
        points.remove(points.size()-1);
    }

    public List<Point> getPoints() {
        return points;
    }

    public Color getFgColor() {
        return fgColor;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
        notifyListeners();
    }

    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
        notifyListeners();
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
        notifyListeners();
    }

    public static String writeToString(FilledPolygon polygon) {
        StringBuilder sb = new StringBuilder();

        sb.append("FPOLY ");
        sb.append(polygon.getPoints().size());
        sb.append(" ");

        List<Point> points = polygon.getPoints();

        for (int i=0;i<points.size();i++){
            Point point = points.get(i);
            sb.append(point.x);
            sb.append(" ");
            sb.append(point.y);
            sb.append(" ");
        }

        Color fColor = polygon.getFgColor();
        Color bColor = polygon.getBgColor();

        sb.append(fColor.getRed()+" ");
        sb.append(fColor.getGreen()+" ");
        sb.append(fColor.getBlue()+" ");
        sb.append(bColor.getRed()+" ");
        sb.append(bColor.getGreen()+" ");
        sb.append(bColor.getBlue());
        sb.append("\n");

        return sb.toString();


    }


    public static FilledPolygon parse(String line) {
        String[] args = line.split(" ");

        List<Point> points = new ArrayList<>();
        for (int i=2;i<args.length-6;i=i+2){
            points.add(new Point(Integer.parseInt(args[i]),Integer.parseInt(args[i+1])));
        }
        int len = args.length;

        int r1 = Integer.parseInt(args[len-6]);
        int g1 = Integer.parseInt(args[len-5]);
        int b1 = Integer.parseInt(args[len-4]);
        int r2 = Integer.parseInt(args[len-3]);
        int g2 = Integer.parseInt(args[len-2]);
        int b2 = Integer.parseInt(args[len-1]);

        return new FilledPolygon(points, new Color(r1, g1, b1), new Color(r2, g2, b2));
    }
}
