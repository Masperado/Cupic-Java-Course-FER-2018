package hr.fer.zemris.java.hw16;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents vector of double elements.
 */
public class Vector {

    /**
     * List of double values used to store vector.
     */
    private List<Double> values;

    /**
     * Basic constructor.
     */
    public Vector() {
        this.values = new ArrayList<>();
    }

    /**
     * This method is used to add value to vector.
     *
     * @param value Value to be added
     */
    public void addValue(double value) {
        this.values.add(value);
    }

    /**
     * Getter for vector values.
     *
     * @return Vector values
     */
    public List<Double> getValues() {
        return values;
    }

    /**
     * This method is used for calculating cosine similarity between two vectors.
     *
     * @param other Vector
     * @return Cosine similarity
     */
    public double cosineSimilarity(Vector other) {

        List<Double> otherValues = other.getValues();

        if (otherValues.size() != values.size()) {
            return 0;
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < values.size(); i++) {
            dotProduct += values.get(i) * otherValues.get(i);
            normA += Math.pow(values.get(i), 2);
            normB += Math.pow(otherValues.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

}
