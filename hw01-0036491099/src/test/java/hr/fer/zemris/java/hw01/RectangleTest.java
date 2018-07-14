package hr.fer.zemris.java.hw01;

import org.junit.Test;

import static org.junit.Assert.*;

public class RectangleTest {

    @Test
    public void circumfenceTest(){
        assertEquals(18.0,Rectangle.circumfence(4,5),1E-7);
    }

    @Test
    public void areaTest(){
        assertEquals(132.0,Rectangle.area(11,12),1E-7);
    }
}