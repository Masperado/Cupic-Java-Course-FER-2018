package hr.fer.zemris.java.hw01;

import org.junit.Test;

import static org.junit.Assert.*;

public class FactorialTest {

    @Test
    public void factorialFor1() {
        assertEquals(1L,Factorial.factorial(1));
    }

    @Test
    public void factorialFor10() {
        assertEquals(3628800L,Factorial.factorial(10));
    }

    @Test
    public void factorialFor20() {
        assertEquals(2432902008176640000L,Factorial.factorial(20));
    }

}