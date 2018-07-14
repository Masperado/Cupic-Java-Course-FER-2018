package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents Raycaster. It is used for displaying very pretty picture which shows lot of balls in 3d space.
 */
public class Raycaster {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(),
                new Point3D(10, 0, 0),
                new Point3D(0, 0, 0),
                new Point3D(0, 0, 10),
                20, 20);
    }

    /**
     * This method is used for constructing {@link IRayTracerProducer} which is used for rendering picture,
     *
     * @return {@link IRayTracerProducer} instance
     */
    private static IRayTracerProducer getIRayTracerProducer() {
        return (eye, view, viewUp, horizontal, vertical, width, height, requestNo, observer) -> {
            System.out.println("Započinjem izračune...");
            short[] red = new short[width * height];
            short[] green = new short[width * height];
            short[] blue = new short[width * height];


            // Find axis
            Point3D og = view.sub(eye);
            Point3D vuv = viewUp.normalize();

            Point3D zAxis = og.normalize();
            Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv))).normalize();
            Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

            // Find screen corner
            Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
                    .add(yAxis.scalarMultiply(vertical / 2));


            // Calculate for each pixel
            Scene scene = RayTracerViewer.createPredefinedScene();
            short[] rgb = new short[3];
            int offset = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1))
                            .sub(yAxis.scalarMultiply(y * vertical / (height - 1))));
                    Ray ray = Ray.fromPoints(eye, screenPoint);
                    tracer(scene, ray, rgb);
                    red[offset] = rgb[0] > 255 ? 255 : rgb[0];
                    green[offset] = rgb[1] > 255 ? 255 : rgb[1];
                    blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
                    offset++;
                }
            }
            System.out.println("Izračuni gotovi...");
            observer.acceptResult(red, green, blue, requestNo);
            System.out.println("Dojava gotova...");
        };
    }

    /**
     * This method is used for calculating scene colors for given ray.
     *
     * @param scene Scene
     * @param ray   Ray
     * @param rgb   Color array
     */
    private static void tracer(Scene scene, Ray ray, short[] rgb) {
        // Black color
        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;
        RayIntersection closest = findClosestIntersection(scene, ray);

        // If there isn't any intersection color must be black
        if (closest == null) {
            return;
        }

        // Ambient color
        rgb[0] = 15;
        rgb[1] = 15;
        rgb[2] = 15;

        // For each light source that is visible from given spot at
        // scene, add color values.
        for (LightSource ls : scene.getLights()) {

            Ray rayL = Ray.fromPoints(ls.getPoint(), closest.getPoint());
            RayIntersection closestIntersection = findClosestIntersection(scene, rayL);

            if (closestIntersection == null) {
                continue;
            }

            if (ls.getPoint().sub(closestIntersection.getPoint()).norm() + 0.01 < ls.getPoint()
                    .sub(closest.getPoint()).norm()) {
                continue;
            }

            closestIntersection = closest;

            double ln = ls.getPoint().sub(closestIntersection.getPoint()).normalize()
                    .scalarProduct(closestIntersection.getNormal());

            rgb[0] += closestIntersection.getKdr() * Math.max(ln, 0) * ls.getR();
            rgb[1] += closestIntersection.getKdg() * Math.max(ln, 0) * ls.getG();
            rgb[2] += closestIntersection.getKdb() * Math.max(ln, 0) * ls.getB();

            // Add reflective component
            Point3D n = closestIntersection.getNormal();
            Point3D l = ls.getPoint().sub(closestIntersection.getPoint());
            Point3D lOnN = n.scalarMultiply(l.scalarProduct(n));
            Point3D r = lOnN.add(lOnN.negate().add(l).scalarMultiply(-1));
            Point3D v = ray.start.sub(closestIntersection.getPoint());
            double cos = r.normalize().scalarProduct(v.normalize());

            if (cos > 0) {
                cos = Math.pow(cos, closestIntersection.getKrn());

                rgb[0] += closestIntersection.getKrr() * cos * ls.getR();
                rgb[1] += closestIntersection.getKrg() * cos * ls.getG();
                rgb[2] += closestIntersection.getKrb() * cos * ls.getB();
            }

        }
    }

    /**
     * This method is used for finding closest {@link RayIntersection} between scene and ray.
     *
     * @param scene Scene
     * @param ray   Ray
     * @return {@link RayIntersection}
     */
    private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
        RayIntersection closest = null;

        for (GraphicalObject object : scene.getObjects()) {

            RayIntersection intersection = object.findClosestRayIntersection(ray);

            if (intersection == null) {
                continue;
            }

            if (closest == null || intersection.getDistance() < closest.getDistance()) {
                closest = intersection;
            }

        }
        return closest;

    }
}
