package hr.fer.zemris.java.hw05.db;

/**
 * This class represents ComparisonOperators. It contains 7 public static final implementations of {@link IComparisonOperator}
 * interface.
 */
public class ComparisonOperators {

    /**
     * Less (<) operator.
     */
    public static final IComparisonOperator LESS = (value1, value2) -> value1.compareTo(value2) < 0;

    /**
     * Less of equals (<=) operator.
     */
    public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;

    /**
     * Greater (>) operator.
     */
    public static final IComparisonOperator GREATER = (value1, value2) -> value1.compareTo(value2) > 0;

    /**
     * Greater or equals (>=) operator.
     */
    public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;

    /**
     * Equals (=) operator.
     */
    public static final IComparisonOperator EQUALS = String::equals;

    /**
     * Not equals (!=) operator.
     */
    public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> !value1.equals(value2);

    /**
     * Like operator. Checks if given string matches the pattern given.
     */
    public static final IComparisonOperator LIKE = (value1, value2) -> {
        int indexOfWildcard = value2.indexOf("*");

        if (indexOfWildcard != -1) {
            if (value2.lastIndexOf("*") != indexOfWildcard) {
                throw new RuntimeException("Višestruki wildcard characteri!");
            }

            if ((value2.length() - 1) > value1.length()) {
                return false;
            }

            if (indexOfWildcard == 0) {
                return value1.endsWith(value2.substring(1));
            } else if (indexOfWildcard == value2.length() - 1) {
                return value1.startsWith(value2.substring(0, value2.length() - 1));
            } else {
                String[] parts = value2.split("\\*");
                return value1.startsWith(parts[0]) && value1.endsWith(parts[1]);
            }
        } else {
            return value1.equals(value2);
        }

    };


}
