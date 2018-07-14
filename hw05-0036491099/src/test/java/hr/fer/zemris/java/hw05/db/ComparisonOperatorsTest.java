package hr.fer.zemris.java.hw05.db;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ComparisonOperatorsTest {

    @Test
    public void lessTrueTest() {
        assertTrue(ComparisonOperators.LESS.satisfied("Ana", "Jasna"));
    }

    @Test
    public void lessFalseTest() {
        assertFalse(ComparisonOperators.LESS.satisfied("Jure", "Antica"));
    }

    @Test
    public void lessOrEqualsTrueTest() {
        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana", "Ana"));
    }

    @Test
    public void lessOrEqualsFalseTest() {
        assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("Jure", "Antica"));
    }

    @Test
    public void greaterTrueTest() {
        assertTrue(ComparisonOperators.GREATER.satisfied("Stipica", "Milica"));
    }

    @Test
    public void greaterFalseTest() {
        assertFalse(ComparisonOperators.GREATER.satisfied("Stipica", "Suzana"));
    }

    @Test
    public void greaterEqualsTrueTest() {
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Ana", "Ana"));
    }

    @Test
    public void greaterEqualsFalseTest() {
        assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Jure", "Špiro"));
    }

    @Test
    public void equalsTrueTest() {
        assertTrue(ComparisonOperators.EQUALS.satisfied("Ana", "Ana"));
    }

    @Test
    public void equalsFalseTest() {
        assertFalse(ComparisonOperators.EQUALS.satisfied("Jure", "Antica"));
    }

    @Test
    public void notEqualsTrueTest() {
        assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Ana", "Jasna"));
    }

    @Test
    public void notEqualsFalseTest() {
        assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Jure", "Jure"));
    }

    @Test
    public void likeTest() {
        assertFalse(ComparisonOperators.LIKE.satisfied("Zagreb", "Aba*"));
        assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
        assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
    }

}