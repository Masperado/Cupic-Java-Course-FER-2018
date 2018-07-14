package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValueWrapperTest {

    @Test
    public void getValue() {
        ValueWrapper wrapper = new ValueWrapper(5);
        assertEquals(5,wrapper.getValue());
    }

    @Test
    public void setValue() {
        ValueWrapper wrapper = new ValueWrapper(5);
        wrapper.setValue("Žorž");
        assertEquals("Žorž",wrapper.getValue());
    }

    @Test
    public void addBothNull() {
        ValueWrapper v1 = new ValueWrapper(null);
        ValueWrapper v2 = new ValueWrapper(null);
        v1.add(v2.getValue());
        assertEquals(0,v1.getValue());
        assertTrue(v1.getValue() instanceof Integer);
        assertEquals(null,v2.getValue());
    }

    @Test
    public void addOneNull() {
        ValueWrapper v1 = new ValueWrapper(null);
        ValueWrapper v2 = new ValueWrapper(4);
        v1.add(v2.getValue());
        assertEquals(4,v1.getValue());
        assertTrue(v1.getValue() instanceof Integer);
        assertEquals(4,v2.getValue());
    }

    @Test
    public void addIntDouble(){
        ValueWrapper v1 = new ValueWrapper(5);
        ValueWrapper v2 = new ValueWrapper(5.5);
        v1.add(v2.getValue());
        assertEquals(10.5,v1.getValue());
        assertTrue(v1.getValue() instanceof Double);
        assertEquals(5.5,v2.getValue());
    }

    @Test
    public void addIntInt(){
        ValueWrapper v1 = new ValueWrapper(5);
        ValueWrapper v2 = new ValueWrapper(5);
        v1.add(v2.getValue());
        assertEquals(10,v1.getValue());
        assertTrue(v1.getValue() instanceof Integer);
        assertEquals(5,v2.getValue());
    }

    @Test
    public void addStringDouble(){
        ValueWrapper v1 = new ValueWrapper("1.2E1");
        ValueWrapper v2 = new ValueWrapper(1);
        v1.add(v2.getValue());
        assertEquals(13.0,v1.getValue());
        assertTrue(v1.getValue() instanceof Double);
        assertEquals(1,v2.getValue());
    }

    @Test
    public void addStringInt(){
        ValueWrapper v1 = new ValueWrapper("12");
        ValueWrapper v2 = new ValueWrapper(1);
        v1.add(v2.getValue());
        assertEquals(13,v1.getValue());
        assertTrue(v1.getValue() instanceof Integer);
        assertEquals(1,v2.getValue());
    }

    @Test(expected = RuntimeException.class)
    public void addStringInvalid(){
        ValueWrapper v1 = new ValueWrapper("Ankica");
        ValueWrapper v2 = new ValueWrapper(1);
        v1.add(v2.getValue());
    }

    @Test
    public void sub() {
        ValueWrapper v1 = new ValueWrapper("1.2E1");
        ValueWrapper v2 = new ValueWrapper(1);
        v1.sub(v2.getValue());
        assertEquals(11.0,v1.getValue());
        assertTrue(v1.getValue() instanceof Double);
        assertEquals(1,v2.getValue());
    }

    @Test
    public void multiply() {
        ValueWrapper v1 = new ValueWrapper("1.2E1");
        ValueWrapper v2 = new ValueWrapper(1);
        v1.multiply(v2.getValue());
        assertEquals(12.0,v1.getValue());
        assertTrue(v1.getValue() instanceof Double);
        assertEquals(1,v2.getValue());
    }

    @Test
    public void divide() {
        ValueWrapper v1 = new ValueWrapper("1.2E1");
        ValueWrapper v2 = new ValueWrapper(6);
        v1.divide(v2.getValue());
        assertEquals(2.0,v1.getValue());
        assertTrue(v1.getValue() instanceof Double);
        assertEquals(6,v2.getValue());
    }

    @Test
    public void numCompareBigger() {
        ValueWrapper v1 = new ValueWrapper("1.2E1");
        ValueWrapper v2 = new ValueWrapper(1);
        assertTrue(v1.numCompare(v2.getValue())>0);
    }

    @Test
    public void numCompareSmaller() {
        ValueWrapper v1 = new ValueWrapper("1.2E1");
        ValueWrapper v2 = new ValueWrapper(120);
        assertTrue(v1.numCompare(v2.getValue())<0);
    }

    @Test
    public void numCompareEquals() {
        ValueWrapper v1 = new ValueWrapper("1.2E1");
        ValueWrapper v2 = new ValueWrapper(12);
        assertTrue(v1.numCompare(v2.getValue())==0);
    }
}