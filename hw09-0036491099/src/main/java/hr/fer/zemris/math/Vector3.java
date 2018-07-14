package hr.fer.zemris.math;

/**
 * This class represents Vector3. It is used for representing vector in 3 dimensional space.
 */
public class Vector3 {

    /**
     * X component.
     */
    private double x;

    /**
     * Y component.
     */
    private double y;

    /**
     * Z component.
     */
    private double z;

    /**
     * Basic constructor.
     *
     * @param x X component
     * @param y Y component
     * @param z Z component
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * This method is used for calculating norm of vector.
     *
     * @return Vector norm
     */
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * This method returns normalized vector.
     *
     * @return Normalized vector
     */
    public Vector3 normalized() {
        double norm = norm();

        return new Vector3(x / norm, y / norm, z / norm);
    }

    /**
     * This method sums two vector.
     *
     * @param other vector which will be added
     * @return Sum of two vectors
     */
    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
    }

    /**
     * This method subs two vector.
     *
     * @param other vector which will be subbed
     * @return Difference of two vectors
     */
    public Vector3 sub(Vector3 other) {
        return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
    }

    /**
     * This method calculates dot product of two vector.
     *
     * @param other vector which will be multiplied with
     * @return Dot product
     */
    public double dot(Vector3 other) {
        return x * other.getX() + y * other.getY() + z * other.getZ();
    }

    /**
     * This method calculates cross product of two vector.
     *
     * @param other vector which will be multiplied with
     * @return Cross product
     */
    public Vector3 cross(Vector3 other) {
        return new Vector3(y * other.getZ() - z * other.getY(), z * other.getX() - x * other.getZ(), x * other.getY() - y * other.getX());
    }

    /**
     * This method scales vector with given factor.
     *
     * @param s Scale factor
     * @return Scaled vector
     */
    public Vector3 scale(double s) {
        return new Vector3(x * s, y * s, z * s);
    }

    /**
     * This method calculates cosine of angle between vectors.
     *
     * @param other Other vector
     * @return Cosine of angle
     */
    public double cosAngle(Vector3 other) {
        return dot(other) / (norm() * other.norm());
    }

    /**
     * Getter for x.
     *
     * @return X component
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for y.
     *
     * @return Y component
     */
    public double getY() {
        return y;
    }

    /**
     * Getter for z.
     *
     * @return Z component
     */
    public double getZ() {
        return z;
    }

    /**
     * This method returns array representation of this vector.
     *
     * @return Array representation
     */
    public double[] toArray() {
        return new double[]{x, y, z};
    }

    /**
     * This method returns String representation of this vector.
     *
     * @return String representation
     */
    public String toString() {
        return String.format("(%6f, %6f, %6f)", x, y, z);
    }
}
