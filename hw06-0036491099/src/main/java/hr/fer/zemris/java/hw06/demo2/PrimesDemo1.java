package hr.fer.zemris.java.hw06.demo2;

/**
 * This class is used for demonstrating use of {@link PrimesCollection}.
 */
public class PrimesDemo1 {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(5);
        for (Integer prime : primesCollection) {
            System.out.println("Got prime: " + prime);
        }
    }
}
