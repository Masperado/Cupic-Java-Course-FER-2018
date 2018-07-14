package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * This class is used for demonstrating use of {@link SimpleHashtable} iterators.
 */
public class Primjer {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);

        for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
        }

        System.out.println();

        for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
            for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
                System.out.printf(
                        "(%s => %d) - (%s => %d)%n",
                        pair1.getKey(), pair1.getValue(),
                        pair2.getKey(), pair2.getValue()
                );
            }
        }

        System.out.println();

        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            if (pair.getKey().equals("Ivana")) {
                iter.remove();
            }
        }

        System.out.println(examMarks);

        System.out.println();

        examMarks.put("Ivana", 5);
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter2 = examMarks.iterator();
        while (iter2.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter2.next();
            if (pair.getKey().equals("Ivana")) {
                try {
                    iter2.remove();
                    iter2.remove();
                } catch (IllegalStateException ex) {
                    System.out.println("Iterator pazi na duplo uklanjanje!");
                }
            }
        }

        System.out.println(examMarks);

        System.out.println();

        examMarks.put("Ivana", 5);
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter3 = examMarks.iterator();

        try {
            while (iter3.hasNext()) {
                SimpleHashtable.TableEntry<String, Integer> pair = iter3.next();
                if (pair.getKey().equals("Ivana")) {
                    examMarks.remove("Ivana");
                }
            }
        } catch (ConcurrentModificationException ex) {
            System.out.println("Iterator dobro pazi na modifikacije");
        }

        System.out.println(examMarks);

        System.out.println();

        examMarks.put("Ivana", 5);

        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter4 = examMarks.iterator();
        while (iter4.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter4.next();
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
            iter4.remove();

        }
        System.out.printf("Veličina: %d%n", examMarks.size());
    }
}
