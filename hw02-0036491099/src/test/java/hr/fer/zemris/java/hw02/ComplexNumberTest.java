package hr.fer.zemris.java.hw02;

import org.junit.Test;

import static org.junit.Assert.*;

public class ComplexNumberTest {

    private static final double DELTA = 1E-7;

    @Test
    public void fromReal() {
        ComplexNumber c1 = ComplexNumber.fromReal(5);
        assertEquals(5,c1.getReal(),DELTA);
        assertEquals(0,c1.getImaginary(),DELTA);
    }

    @Test
    public void fromImaginary() {
        ComplexNumber c1 = ComplexNumber.fromImaginary(5);
        assertEquals(0,c1.getReal(),DELTA);
        assertEquals(5,c1.getImaginary(),DELTA);
    }

    @Test
    public void fromMagnitudeAndAngle() {
        ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(5,Math.PI/2);
        assertEquals(0,c1.getReal(),DELTA);
        assertEquals(5,c1.getImaginary(),DELTA);
    }

    @Test
    public void parseNormalDecimalNegativeRealPositiveImaginary() {

        ComplexNumber c1 = ComplexNumber.parse("-2.71+3.51i");
        assertEquals(-2.71, c1.getReal(), DELTA);
        assertEquals(3.51, c1.getImaginary(), DELTA);
    }


    @Test
    public void parseNormalDecimalPositiveRealNegativeImaginary() {

        ComplexNumber c1 = ComplexNumber.parse("2.71-3.51i");
        assertEquals(2.71, c1.getReal(), DELTA);
        assertEquals(-3.51, c1.getImaginary(), DELTA);
    }


    @Test
    public void parseDecimalWithImaginaryEqualsMinusOne() {

        ComplexNumber c1 = ComplexNumber.parse("-2.71-i");
        assertEquals(-2.71, c1.getReal(), DELTA);
        assertEquals(-1, c1.getImaginary(), DELTA);
    }


    @Test
    public void parseDecimalWithImaginaryEqualsOne() {

        ComplexNumber c1 = ComplexNumber.parse("-2.71+i");
        assertEquals(-2.71, c1.getReal(), DELTA);
        assertEquals(1, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseImaginaryEqualsOneNoReal() {

        ComplexNumber c1 = ComplexNumber.parse("+i");
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(1, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseImaginaryEqualsMinusOneNoReal() {

        ComplexNumber c1 = ComplexNumber.parse("-i");
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(-1, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseNegativeImaginary() {
        ComplexNumber c1 = ComplexNumber.parse("-2.71i");
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(-2.71, c1.getImaginary(), DELTA);
    }

    @Test
    public void parsePositiveImaginary() {
        ComplexNumber c1 = ComplexNumber.parse("2.71i");
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(2.71, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseNegativeReal() {
        ComplexNumber c1 = ComplexNumber.parse("-2.71");
        assertEquals(-2.71, c1.getReal(), DELTA);
        assertEquals(0, c1.getImaginary(), DELTA);
    }

    @Test
    public void parsePositiveReal() {
        ComplexNumber c1 = ComplexNumber.parse("2");
        assertEquals(2, c1.getReal(), DELTA);
        assertEquals(0, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseRealEqualOne() {

        ComplexNumber c1 = ComplexNumber.parse("1");
        assertEquals(1, c1.getReal(), DELTA);
        assertEquals(0, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseRealEqualsMinusOne() {

        ComplexNumber c1 = ComplexNumber.parse("-1");
        assertEquals(-1, c1.getReal(), DELTA);
        assertEquals(0, c1.getImaginary(), DELTA);
    }

    @Test(expected = RuntimeException.class)
    public void parseŠtefica(){
        ComplexNumber c1 = ComplexNumber.parse("Štefica");
    }

    @Test
    public void getReal() {
        ComplexNumber c1 = new ComplexNumber(2,4);
        assertEquals(2,c1.getReal(),DELTA);
    }

    @Test
    public void getImaginary() {
        ComplexNumber c1 = new ComplexNumber(2,4);
        assertEquals(4,c1.getImaginary(),DELTA);
    }

    @Test
    public void getMagnitude() {
        ComplexNumber c1 = new ComplexNumber(3,4);
        assertEquals(5,c1.getMagnitude(),DELTA);
    }

    @Test
    public void getAngle() {
        ComplexNumber c1 = new ComplexNumber(0,5);
        assertEquals(Math.PI/2,c1.getAngle(),DELTA);

    }

    @Test
    public void add() {
        ComplexNumber c1 = new ComplexNumber(3,4);
        ComplexNumber c2 = new ComplexNumber(4,5);

        ComplexNumber c3 = c1.add(c2);

        assertEquals(7,c3.getReal(),DELTA);
        assertEquals(9,c3.getImaginary(),DELTA);
    }

    @Test
    public void sub() {
        ComplexNumber c1 = new ComplexNumber(3,4);
        ComplexNumber c2 = new ComplexNumber(4,5);

        ComplexNumber c3 = c1.sub(c2);

        assertEquals(-1,c3.getReal(),DELTA);
        assertEquals(-1,c3.getImaginary(),DELTA);

    }

    @Test
    public void mul() {
        ComplexNumber c1 = new ComplexNumber(3,4);
        ComplexNumber c2 = new ComplexNumber(4,5);

        ComplexNumber c3 = c1.mul(c2);

        assertEquals(-8,c3.getReal(),DELTA);
        assertEquals(31,c3.getImaginary(),DELTA);
    }

    @Test
    public void div() {
        ComplexNumber c1 = new ComplexNumber(3,4);
        ComplexNumber c2 = new ComplexNumber(4,5);

        ComplexNumber c3 = c1.div(c2);

        assertEquals(32.0/41,c3.getReal(),DELTA);
        assertEquals(1.0/41,c3.getImaginary(),DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void divByZero(){
        ComplexNumber c1 = new ComplexNumber(1,1);
        ComplexNumber c2 = new ComplexNumber(0,0);

        c1.div(c2);
    }



    @Test
    public void power() {
        ComplexNumber c1 = new ComplexNumber(4,5);
        ComplexNumber c2 = c1.power(5);

        assertEquals(-2476,c2.getReal(),DELTA);
        assertEquals(-10475,c2.getImaginary(),DELTA);

    }

    @Test
    public void root() {
        ComplexNumber c1 = new ComplexNumber(4,5);
        ComplexNumber[] roots = c1.root(3);

        ComplexNumber root1 = roots[0];
        ComplexNumber root2 = roots[1];
        ComplexNumber root3 = roots[2];

        assertEquals(1.77472026,root1.getReal(),DELTA);
        assertEquals(0.54642956,root1.getImaginary(),DELTA);

        assertEquals(-1.3605819,root2.getReal(),DELTA);
        assertEquals(1.26373807,root2.getImaginary(),DELTA);

        assertEquals(-0.4141382,root3.getReal(),DELTA);
        assertEquals(-1.8101676,root3.getImaginary(),DELTA);


    }

}