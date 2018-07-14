package hr.fer.zemris.java.hw05.db;

/**
 * This class represents conditional expression. It is used for representing one query. Each expression has {@link IFieldValueGetter},
 * {@link String} literal and {@link IComparisonOperator}.
 */
public class ConditionalExpression {

    /**
     * Field getter.
     */
    private IFieldValueGetter fieldGetter;

    /**
     * String literal.
     */
    private String stringLiteral;

    /**
     * Comparison operator.
     */
    private IComparisonOperator comparisonOperator;

    /**
     * Basic constructor.
     *
     * @param fieldGetter        Field getter
     * @param stringLiteral      String literal
     * @param comparisonOperator Comparison operator
     */
    public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
        this.fieldGetter = fieldGetter;
        this.stringLiteral = stringLiteral;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * Getter for field getter.
     *
     * @return field getter
     */
    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    /**
     * Getter for String literal.
     *
     * @return String literal
     */
    public String getStringLiteral() {
        return stringLiteral;
    }

    /**
     * Getter for comparison operator.
     *
     * @return Comparison operator
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }
}
