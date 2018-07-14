package hr.fer.zemris.java.custom.collections;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTest {

    Dictionary dictionary;

    @Before
    public void setUp() throws Exception {
        dictionary = new Dictionary();
    }

    @Test
    public void isEmpty() {
        assertTrue(dictionary.isEmpty());
        dictionary.put(5, 7);
        assertFalse(dictionary.isEmpty());
    }

    @Test
    public void size() {
        assertEquals(0, dictionary.size());
        dictionary.put(5, 7);
        assertEquals(1, dictionary.size());
        dictionary.put(4, 5);
        dictionary.put("Dino", "Kresinger");
        assertEquals(3, dictionary.size());
    }

    @Test
    public void clear() {
        assertEquals(0, dictionary.size());

        dictionary.put(5, 7);
        dictionary.put(4, 5);
        dictionary.put("Dino", "Kresinger");
        assertEquals(3, dictionary.size());
        dictionary.clear();
        assertEquals(0, dictionary.size());
    }

    @Test
    public void putNewElements() {
        assertEquals(0, dictionary.size());
        dictionary.put(5.4, 6);
        dictionary.put("Mato", "Neretljak");
        assertEquals(2, dictionary.size());
    }

    @Test
    public void putExistingElements() {
        assertEquals(0, dictionary.size());
        dictionary.put(1, 5);
        dictionary.put(1, 6);
        assertEquals(1, dictionary.size());
        dictionary.put("Petar", "Krpan");
        dictionary.put("Petar", "Bočkaj");
        assertEquals(2, dictionary.size());
    }

    @Test
    public void getNewElement() {
        dictionary.put(1, 5);
        assertEquals(5, dictionary.get(1));
    }

    @Test
    public void getOverridenElements() {
        dictionary.put("Dragan", "Blatnjak");
        dictionary.put("Dragan", "Stojković-Piksi");
        assertEquals("Stojković-Piksi", dictionary.get("Dragan"));
    }

}