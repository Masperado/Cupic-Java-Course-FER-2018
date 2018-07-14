package hr.fer.zemris.java.custom.collections;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class ArrayIndexedCollectionTest {

    ArrayIndexedCollection collection;

    @Before
    public void initialize(){
        collection = new ArrayIndexedCollection();
    }

    @Test
    public void add() {
        collection.add(52);
        collection.add("Jure");

        Object[] array = collection.toArray();

        assertEquals(52,array[0]);
        assertTrue("Jure".equals(array[1]));
    }

    @Test(expected = NullPointerException.class)
    public void addNull(){
        collection.add(null);
    }

    @Test
    public void get() {
        collection.add(52);

        assertEquals(52,collection.get(0));

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getInvalidIndex(){
        collection.get(52);
    }

    @Test
    public void clear() {
        collection.add(42);
        collection.clear();
        assertEquals(0,collection.size());
    }

    @Test
    public void insert() {
        collection.add(42);
        collection.add("Stipe");
        collection.insert(89,1);

        assertEquals(89,collection.get(1));

    }

    @Test(expected = NullPointerException.class)
    public void insertNull(){
        collection.insert(null,4);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void insertInvalidIndex(){
        collection.insert(42,42);
    }

    @Test
    public void indexOf() {
        collection.add(1);
        collection.add(2);
        collection.add(3);

        assertEquals(2,collection.indexOf(3));

    }

    @Test
    public void indexOfNull(){
        assertEquals(-1,collection.indexOf(null));
    }

    @Test
    public void removeIndex() {
        collection.add(5);
        collection.add(3);
        collection.add(2);
        collection.remove(1);

        assertEquals(2,collection.get(1));
        assertEquals(2,collection.size());

    }

    @Test
    public void removeElement(){
        collection.add(5);
        collection.add("Stipe");
        collection.add(2);

        collection.remove("Stipe");

        assertEquals(2,collection.get(1));
    }

    @Test
    public void size() {
        collection.add(5);
        collection.add(9);

        assertEquals(2,collection.size());
    }

    @Test
    public void contains() {
        collection.add(5);
        collection.add(7);

        assertTrue(collection.contains(5));
        assertTrue(collection.contains(7));

    }

    @Test
    public void toArray() {
        collection.add(1);
        collection.add(2);
        collection.add(3);
        collection.add(4);

        Object[] array = collection.toArray();

        assertEquals(1,array[0]);
        assertEquals(2,array[1]);
        assertEquals(3,array[2]);
        assertEquals(4,array[3]);
    }

    @Test
    public void forEach() {
        class TestProcessor extends Processor{
            int i=0;

            @Override
            public void process(Object value){
                i+=2;
            }
        }

        collection.add(1);
        collection.add(2);
        collection.add(3);

        TestProcessor testProcessor = new TestProcessor();

        collection.forEach(testProcessor);

        assertEquals(6,testProcessor.i);

    }

    @Test
    public void isEmpty() {

        assertTrue(collection.isEmpty());

        collection.add(5);

        assertFalse(collection.isEmpty());
    }

    @Test
    public void addAll() {
        ArrayIndexedCollection testCollection = new ArrayIndexedCollection();
        testCollection.add(5);
        testCollection.add(6);
        testCollection.add(7);

        collection.addAll(testCollection);

        assertEquals(5,collection.get(0));
        assertEquals(6,collection.get(1));
        assertEquals(7,collection.get(2));

    }
}