package hr.fer.zemris.java.hw05.db;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QueryFilterTest {

    @Test
    public void testTrueQuery() {
        QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName LIKE \"O*ć\"");
        StudentRecord record = new StudentRecord("0123456789", "Obilinović", "Romano", "80%");
        QueryFilter filter = new QueryFilter(qp2.getQuery());

        assertTrue(filter.accepts(record));

    }

    @Test
    public void testFalseQuery() {
        QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName > \"R\"");
        StudentRecord record = new StudentRecord("0123456789", "Obilinović", "Romano", "80%");
        QueryFilter filter = new QueryFilter(qp2.getQuery());

        assertFalse(filter.accepts(record));

    }

}