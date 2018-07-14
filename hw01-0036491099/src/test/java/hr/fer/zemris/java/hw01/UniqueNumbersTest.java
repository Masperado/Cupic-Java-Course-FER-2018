package hr.fer.zemris.java.hw01;

import org.junit.Test;

import static hr.fer.zemris.java.hw01.UniqueNumbers.*;
import static org.junit.Assert.*;

public class UniqueNumbersTest {

    @Test
    public void addNodeValue() {
        TreeNode glava = null;

        glava = addNode(glava, 42);

        assertEquals(42, glava.value);

    }

    @Test
    public void addNodeLeft() {
        TreeNode glava = null;

        glava = addNode(glava, 42);

        glava = addNode(glava, 21);

        assertEquals(21, glava.left.value);

    }

    @Test
    public void addNodeRight() {
        TreeNode glava = null;

        glava = addNode(glava, 42);

        glava = addNode(glava, 84);

        assertEquals(84, glava.right.value);

    }

    @Test
    public void treeSizeEmpty() {

        TreeNode glava = null;

        assertEquals(0, treeSize(glava));
    }

    @Test
    public void treeSizeBasic() {
        TreeNode glava = null;

        glava = addNode(glava, 1);
        glava = addNode(glava, 2);
        glava = addNode(glava, 3);

        assertEquals(3, treeSize(glava));
    }

    @Test
    public void containsValueFalse() {

        TreeNode glava = null;
        glava = addNode(glava, 42);
        glava = addNode(glava, 21);
        glava = addNode(glava, 84);

        assertFalse(containsValue(glava, 3));
    }

    @Test
    public void containsValueTrue() {

        TreeNode glava = null;
        glava = addNode(glava, 42);
        glava = addNode(glava, 21);
        glava = addNode(glava, 84);

        assertTrue(containsValue(glava, 21));
    }
}