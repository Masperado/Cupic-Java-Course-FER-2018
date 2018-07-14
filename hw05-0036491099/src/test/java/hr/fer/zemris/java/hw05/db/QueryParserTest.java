package hr.fer.zemris.java.hw05.db;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueryParserTest {

    @Test
    public void directQuery() {
        QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
        assertTrue(qp1.isDirectQuery());
        assertEquals("0123456789", qp1.getQueriedJmbag());
        assertEquals(1, qp1.getQuery().size());
    }

    @Test
    public void complexQuery() {
        QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
        assertFalse(qp2.isDirectQuery());
        assertEquals(2, qp2.getQuery().size());
    }

}