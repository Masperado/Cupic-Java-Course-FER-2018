package hr.fer.zemris.java.hw05.collections;

/**
 * This class is used for demonstrating use of {@link SimpleHashtable}.
 */
public class Demo {

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
        examMarks.put("Ivana", 5);
        Integer kristinaGrade = examMarks.get("Kristina");
        System.out.println("Kristina's exam grade is: " + kristinaGrade);
        System.out.println("Number of stored pairs: " + examMarks.size());

    }
}
