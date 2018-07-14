package hr.fer.zemris.java.gui.prim;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrimListModelTest {

    @Test
    public void getSize() {
        PrimListModel model = new PrimListModel();
        assertEquals(1, model.getSize());
        model.next();
        assertEquals(2, model.getSize());
    }

    @Test
    public void getElementAt() {
        PrimListModel model = new PrimListModel();
        assertEquals(1L, (long) model.getElementAt(0));
        model.next();
        model.next();
        assertEquals(3L, (long) model.getElementAt(2));

    }

    @Test
    public void next() {
        PrimListModel model = new PrimListModel();
        model.next();
        model.next();
        model.next();
        model.next();
        assertEquals(7L, (long) model.getElementAt(4));
    }
}