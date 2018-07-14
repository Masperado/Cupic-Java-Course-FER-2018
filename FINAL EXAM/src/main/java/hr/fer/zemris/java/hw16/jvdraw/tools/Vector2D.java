package hr.fer.zemris.java.hw16.jvdraw.tools;

/**
 * This class represents Vector in 2D space. Each vector has x and y cartesian coordinates and support basic vector
 * operations.
 */
public class Vector2D {

    /**
     * X component of vector.
     */
    private double x;

    /**
     * Y component of vector.
     */
    private double y;

    private double z;

    /**
     * Basic constructor.
     *
     * @param x X component
     * @param y Y component
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
        this.z=0;
    }

    /**
     * Getter for x component
     *
     * @return X component
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for y component.
     *
     * @return Y component
     */
    public double getY() {
        return y;
    }

    /**
     * This method is used for translating this vector with given offset.
     *
     * @param offset Offset vector
     */
    public void translate(Vector2D offset) {
        x = x + offset.getX();
        y = y + offset.getY();
    }

    /**
     * This method is used for translating this vector with given offset.
     *
     * @param offset Offset vector
     * @return Translated vector
     */
    public Vector2D translated(Vector2D offset) {
        return new Vector2D(x + offset.getX(), y + offset.getY());
    }

    /**
     * This method is used for rotating this vector with given angle.
     *
     * @param angle Angle
     */
    public void rotate(double angle) {
        double rotatedX = x * Math.cos(angle) - y * Math.sin(angle);
        double rotatedY = x * Math.sin(angle) + y * Math.cos(angle);

        x = rotatedX;
        y = rotatedY;
    }

    /**
     * This method is used for rotating this vector with given angle.
     *
     * @param angle Angle
     * @return Rotated vector
     */
    public Vector2D rotated(double angle) {
        double rotatedX = x * Math.cos(angle) - y * Math.sin(angle);
        double rotatedY = x * Math.sin(angle) + y * Math.cos(angle);

        return new Vector2D(rotatedX, rotatedY);

    }

    /**
     * This method is used for scaling this vector with given scalar.
     *
     * @param scaler Scalar
     */
    public void scale(double scaler) {
        x *= scaler;
        y *= scaler;
    }

    /**
     * This method is used for scaling this vector with given scalar.
     *
     * @param scaler Scaler
     * @return Scaled vector
     */
    public Vector2D scaled(double scaler) {
        return new Vector2D(x * scaler, y * scaler);
    }

    /**
     * This method is used for copying this vector.
     *
     * @return Copied vector
     */
    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    /**
     * This method is used for normalizing this vector.
     *
     * @return Normalized vector
     */
    public Vector2D normalized() {
        double magnitude = Math.sqrt(x * x + y * y);

        if (magnitude <= 0) {
            return new Vector2D(x, y);
        }

        return new Vector2D(x / magnitude, y / magnitude);
    }

    public double zComponentOfProduct(Vector2D other){


        return this.x*other.y-this.y*other.x;
    };

    public Vector2D sub(Vector2D v1) {
        return new Vector2D(this.x-v1.x,this.y-v1.y);
    }
}
