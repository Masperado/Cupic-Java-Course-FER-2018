package hr.fer.zemris.java.hw05.db;

/**
 * This interface represents ComparisonOperator. It defines one method, satisfied, which is used for telling if this ComparsionOperator is satisified.
 */
public interface IComparisonOperator {

    /**
     * This method is used for testing if two given Strings satisfy this ComparsionOperator.
     *
     * @param value1 First string
     * @param value2 Second string
     * @return True if they satisfy, false otherwise
     */
    public boolean satisfied(String value1, String value2);
}
