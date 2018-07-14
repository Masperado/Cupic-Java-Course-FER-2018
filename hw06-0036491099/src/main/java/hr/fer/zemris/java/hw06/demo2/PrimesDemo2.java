package hr.fer.zemris.java.hw06.demo2;

/**
 * This class is used for demonstrating use of {@link PrimesCollection}.
 */
public class PrimesDemo2 {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(2);
        for (Integer prime : primesCollection) {
            for (Integer prime2 : primesCollection) {
                System.out.println("Got prime pair: " + prime + ", " + prime2);
            }
        }
    }
}
