package hr.fer.zemris.math;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2DTest {

    private static final double DELTA = 1E-7;

    @Test
    public void getX() {
        Vector2D vector = new Vector2D(4, 5);

        assertEquals(4.0, vector.getX(), DELTA);
    }

    @Test
    public void getY() {
        Vector2D vector = new Vector2D(4, 5);

        assertEquals(5.0, vector.getY(), DELTA);
    }

    @Test
    public void translate() {

        Vector2D vector = new Vector2D(10.0, 10.0);
        Vector2D translating = new Vector2D(5, -5);

        vector.translate(translating);

        assertEquals(15.0, vector.getX(), DELTA);
        assertEquals(5, vector.getY(), DELTA);

        assertEquals(5, translating.getX(), DELTA);
        assertEquals(-5, translating.getY(), DELTA);
    }

    @Test
    public void translated() {

        Vector2D vector = new Vector2D(10.0, 10.0);
        Vector2D translating = new Vector2D(5, -5);

        Vector2D translatedVector = vector.translated(translating);

        assertEquals(15.0, translatedVector.getX(), DELTA);
        assertEquals(5, translatedVector.getY(), DELTA);

        assertEquals(10.0, vector.getX(), DELTA);
        assertEquals(10, vector.getY(), DELTA);

        assertEquals(5, translating.getX(), DELTA);
        assertEquals(-5, translating.getY(), DELTA);

    }

    @Test
    public void rotate() {
        Vector2D vector = new Vector2D(10, 10);

        vector.rotate(Math.PI / 4);

        assertEquals(0, vector.getX(), DELTA);
        assertEquals(10 * Math.sqrt(2), vector.getY(), DELTA);

    }

    @Test
    public void rotated() {

        Vector2D vector = new Vector2D(10, 10);

        Vector2D rotated = vector.rotated(Math.PI / 4);

        assertEquals(0, rotated.getX(), DELTA);
        assertEquals(10 * Math.sqrt(2), rotated.getY(), DELTA);

        assertEquals(10.0, vector.getX(), DELTA);
        assertEquals(10, vector.getY(), DELTA);

    }

    @Test
    public void scale() {

        Vector2D vector = new Vector2D(1, 2);

        vector.scale(0.5);

        assertEquals(0.5, vector.getX(), DELTA);
        assertEquals(1, vector.getY(), DELTA);
    }

    @Test
    public void scaled() {
        Vector2D vector = new Vector2D(1, 2);

        Vector2D scaled = vector.scaled(0.5);

        assertEquals(0.5, scaled.getX(), DELTA);
        assertEquals(1, scaled.getY(), DELTA);

        assertEquals(1, vector.getX(), DELTA);
        assertEquals(2, vector.getY(), DELTA);


    }

    @Test
    public void copy() {
        Vector2D vector = new Vector2D(3, 4);
        Vector2D copy = vector.copy();

        assertEquals(3, copy.getX(), DELTA);
        assertEquals(4, copy.getY(), DELTA);

        assertEquals(3, vector.getX(), DELTA);
        assertEquals(4, vector.getY(), DELTA);

    }

    @Test
    public void normalized() {
        Vector2D vector = new Vector2D(3, 4);

        Vector2D normalized = vector.normalized();

        assertEquals(0.6, normalized.getX(), DELTA);
        assertEquals(0.8, normalized.getY(), DELTA);

        assertEquals(3, vector.getX(), DELTA);
        assertEquals(4, vector.getY(), DELTA);
    }
}