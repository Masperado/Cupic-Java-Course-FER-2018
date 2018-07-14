package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * This class represents Raycaster parallel. It is used for displaying very pretty picture which shows lot of balls in
 * 3d space.
 */
public class RaycasterParallel {

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


            Scene scene = RayTracerViewer.createPredefinedScene();

            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(new CasterJob(0, height - 1, width, height, horizontal, vertical, xAxis, yAxis, eye,
                    red, green, blue, screenCorner, scene));
            pool.shutdown();


            System.out.println("Izračuni gotovi...");
            observer.acceptResult(red, green, blue, requestNo);
            System.out.println("Dojava gotova...");
        };
    }


    /**
     * This class represents CasterJob. It is used for paralleling caster computations.
     */
    private static class CasterJob extends RecursiveAction {

        /**
         * Minimum y.
         */
        private int yMin;

        /**
         * Maximum y.
         */
        private int yMax;

        /**
         * Width of screen.
         */
        private int width;

        /**
         * Height of screen.
         */
        private int height;

        /**
         * Horizontal length.
         */
        private double horizontal;

        /**
         * Vertical length.
         */
        private double vertical;

        /**
         * X axis.
         */
        private Point3D xAxis;

        /**
         * Y axis.
         */
        private Point3D yAxis;

        /**
         * Eye.
         */
        private Point3D eye;

        /**
         * Red color array.
         */
        private short[] red;

        /**
         * Green color array.
         */
        private short[] green;

        /**
         * Blue color array.
         */
        private short[] blue;

        /**
         * Screen corner.
         */
        private Point3D screenCorner;

        /**
         * Scene.
         */
        private Scene scene;

        /**
         * Minimum number of y tracks.
         */
        private final int treshold = 16 * 16;

        /**
         * @param yMin         Minimum y
         * @param yMax         Maximum y
         * @param width        Width of screen
         * @param height       Height of screen
         * @param horizontal   Horizontal length
         * @param vertical     Vertical length
         * @param xAxis        X axis
         * @param yAxis        Y axis
         * @param eye          Eye
         * @param red          Red color array
         * @param green        Green color array
         * @param blue         Blue color array
         * @param screenCorner Screen corner
         * @param scene        Scene
         */
        public CasterJob(int yMin, int yMax, int width, int height, double horizontal, double vertical, Point3D xAxis, Point3D yAxis, Point3D eye, short[] red, short[] green, short[] blue, Point3D screenCorner, Scene scene) {
            this.yMin = yMin;
            this.yMax = yMax;
            this.width = width;
            this.height = height;
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.xAxis = xAxis;
            this.yAxis = yAxis;
            this.eye = eye;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.screenCorner = screenCorner;
            this.scene = scene;
        }

        @Override
        protected void compute() {
            if (yMax - yMin + 1 <= treshold) {
                computeDirect();
                return;
            }

            invokeAll(
                    new CasterJob(yMin, yMin + (yMax - yMin) / 2, width, height, horizontal, vertical, xAxis, yAxis,
                            eye, red, green, blue, screenCorner, scene),
                    new CasterJob(yMin + (yMax - yMin) / 2 + 1, yMax, width, height, horizontal, vertical, xAxis,
                            yAxis, eye, red, green, blue, screenCorner, scene));
        }

        /**
         * This method is used for computing colors of raycaster.
         */
        private void computeDirect() {
            short[] rgb = new short[3];
            for (int y = yMin; y < yMax; y++) {
                for (int x = 0; x < width; x++) {
                    Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1))
                            .sub(yAxis.scalarMultiply(y * vertical / (height - 1))));
                    Ray ray = Ray.fromPoints(eye, screenPoint);
                    tracer(scene, ray, rgb);
                    red[y * height + x] = rgb[0] > 255 ? 255 : rgb[0];
                    green[y * height + x] = rgb[1] > 255 ? 255 : rgb[1];
                    blue[y * height + x] = rgb[2] > 255 ? 255 : rgb[2];
                }
            }
        }
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
