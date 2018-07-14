package hr.fer.zemris.math;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Vector3Test {

    private static final double DELTA = 1E-7;

    @Test
    public void norm() {
        Vector3 vector = new Vector3(3, 4, 5);
        assertEquals(Math.sqrt(50), vector.norm(), DELTA);
    }

    @Test
    public void normalized() {
        Vector3 vector = new Vector3(3, 4, 5).normalized();
        assertEquals(3 / Math.sqrt(50), vector.getX(), DELTA);
        assertEquals(4 / Math.sqrt(50), vector.getY(), DELTA);
        assertEquals(5 / Math.sqrt(50), vector.getZ(), DELTA);
    }

    @Test
    public void add() {
        Vector3 vector = new Vector3(3, 4, 5).add(new Vector3(1, 2, 3));
        assertEquals(4, vector.getX(), DELTA);
        assertEquals(6, vector.getY(), DELTA);
        assertEquals(8, vector.getZ(), DELTA);
    }

    @Test
    public void sub() {
        Vector3 vector = new Vector3(3, 4, 5).sub(new Vector3(1, 2, 3));
        assertEquals(2, vector.getX(), DELTA);
        assertEquals(2, vector.getY(), DELTA);
        assertEquals(2, vector.getZ(), DELTA);
    }

    @Test
    public void dot() {
        double dot = new Vector3(3, 4, 5).dot(new Vector3(1, 2, 3));
        assertEquals(26, dot, DELTA);
    }

    @Test
    public void cross() {
        Vector3 vector = new Vector3(3, 4, 5).add(new Vector3(1, 2, 3));
        assertEquals(4, vector.getX(), DELTA);
        assertEquals(6, vector.getY(), DELTA);
        assertEquals(8, vector.getZ(), DELTA);
    }

    @Test
    public void scale() {
        Vector3 vector = new Vector3(3, 4, 5).scale(2);
        assertEquals(6, vector.getX(), DELTA);
        assertEquals(8, vector.getY(), DELTA);
        assertEquals(10, vector.getZ(), DELTA);
    }

    @Test
    public void cosAngle() {
        double angle = new Vector3(3, 4, 5).cosAngle(new Vector3(1, 2, 3));
        assertEquals(0.9827076298, angle, DELTA);
    }

    @Test
    public void getXYZ() {
        Vector3 vector = new Vector3(3, 4, 5);
        assertEquals(3, vector.getX(), DELTA);
        assertEquals(4, vector.getY(), DELTA);
        assertEquals(5, vector.getZ(), DELTA);
    }

    @Test
    public void toArray() {
        double[] array = new Vector3(3, 4, 5).toArray();
        assertEquals(3, array[0], DELTA);
        assertEquals(4, array[1], DELTA);
        assertEquals(5, array[2], DELTA);
    }
}