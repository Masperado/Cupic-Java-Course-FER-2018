package hr.fer.zemris.java.hw07.crypto;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UtilTest {


    @Test
    public void testHextobyteEmpty() {
        String keyText = "";

        byte[] expected = new byte[]{};

        byte[] actual = Util.hextobyte(keyText);

        assertArrayEquals(expected, actual);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testHextobyteInvalid() {
        String keyText = "01a";

        byte[] actual = Util.hextobyte(keyText);

    }

    @Test
    public void testHextobyte() {
        String keyText = "01aE22";

        byte[] expected = new byte[]{1, -82, 34};

        byte[] actual = Util.hextobyte(keyText);

        assertArrayEquals(expected, actual);

    }

    @Test
    public void testBytetohex() {
        byte[] bytes = new byte[]{1, -82, 34};

        String expected = "01ae22";

        String actual = Util.bytetohex(bytes);

        assertEquals(expected, actual);
    }

}