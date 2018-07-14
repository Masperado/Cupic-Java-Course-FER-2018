package hr.fer.zemris.java.raytracer.model;

/**
 * This class is used for representing Sphere. Sphere is instance of {@link GraphicalObject} and is used for
 * representing sphere in {@link hr.fer.zemris.java.raytracer.Raycaster}.
 */
public class Sphere extends GraphicalObject {

    /**
     * Center of sphere.
     */
    private Point3D center;

    /**
     * Radius of sphere.
     */
    private double radius;

    /**
     * Red diffuse coefficient.
     */
    private double kdr;

    /**
     * Green diffuse coefficient.
     */
    private double kdg;

    /**
     * Blue diffuse coefficient.
     */
    private double kdb;

    /**
     * Red reflective coefficient.
     */
    private double krr;

    /**
     * Green reflective coefficient.
     */
    private double krg;

    /**
     * Blue reflective coefficient.
     */
    private double krb;

    /**
     * N reflective coefficient.
     */
    private double krn;

    /**
     * Basic constructor.
     *
     * @param center Center of sphere
     * @param radius Radisu of sphere
     * @param kdr    Red diffuse coefficient
     * @param kdg    Green diffuse coefficient
     * @param kdb    Blue diffuse coefficient
     * @param krr    Red reflection coefficient
     * @param krg    Green reflection coefficient
     * @param krb    Blue reflection coefficient
     * @param krn    N reflection coefficient
     */
    public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double
            krb, double krn) {
        this.center = center;
        this.radius = radius;
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.krr = krr;
        this.krg = krg;
        this.krb = krb;
        this.krn = krn;
    }

    @Override
    public RayIntersection findClosestRayIntersection(Ray ray) {

        Point3D origin = ray.start;
        Point3D direction = ray.direction;

        double discriminant = Math.pow(direction.scalarProduct(origin.sub(center)), 2)
                - Math.pow((origin.sub(center)).norm(), 2) + radius * radius;

        if (discriminant < 0) {
            return null;
        }

        double distance = -direction.scalarProduct(origin.sub(center)) - Math.sqrt(discriminant);

        Point3D intersection = origin.add(direction.scalarMultiply(distance));


        return new RayIntersection(intersection, distance, true) {
            @Override
            public Point3D getNormal() {
                return intersection.sub(center).normalize();
            }

            @Override
            public double getKdr() {
                return kdr;
            }

            @Override
            public double getKdg() {
                return kdg;
            }

            @Override
            public double getKdb() {
                return kdb;
            }

            @Override
            public double getKrr() {
                return krr;
            }

            @Override
            public double getKrg() {
                return krg;
            }

            @Override
            public double getKrb() {
                return krb;
            }

            @Override
            public double getKrn() {
                return krn;
            }
        };

    }
}