package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectMultistackTest {

    private ObjectMultistack stack;

    @Before
    public void setUp(){
        this.stack = new ObjectMultistack();

    }

    @Test
    public void push() {
        stack.push("Dragan",new ValueWrapper(4));
        stack.push("Dragan",new ValueWrapper(5));
        stack.push("Milojko",new ValueWrapper(7));

        assertEquals(5, stack.peek("Dragan").getValue());
        assertEquals(7, stack.peek("Milojko").getValue());
    }

    @Test
    public void pop() {
        stack.push("Dragan",new ValueWrapper(4));
        stack.push("Dragan",new ValueWrapper(5));
        stack.push("Milojko",new ValueWrapper(7));

        assertEquals(5, stack.pop("Dragan").getValue());
        assertEquals(7, stack.pop("Milojko").getValue());
    }

    @Test
    public void peek() {
        stack.push("Romano",new ValueWrapper(4));
        stack.push("Romano",new ValueWrapper(5));
        stack.push("Obilinović",new ValueWrapper(7));

        assertEquals(5, stack.peek("Romano").getValue());
        assertEquals(7, stack.peek("Obilinović").getValue());
    }

    @Test
    public void isEmpty() {
        assertTrue(stack.isEmpty("Dragan"));
        assertTrue(stack.isEmpty("Milojko"));
        stack.push("Dragan",new ValueWrapper(4));
        stack.push("Dragan",new ValueWrapper(5));
        stack.push("Milojko",new ValueWrapper(7));
        assertFalse(stack.isEmpty("Dragan"));
        assertFalse(stack.isEmpty("Milojko"));
        assertTrue(stack.isEmpty("Ibro"));
    }
}