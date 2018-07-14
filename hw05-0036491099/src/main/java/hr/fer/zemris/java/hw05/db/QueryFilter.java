package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * This class represents QueryFilter. It is used for checking if given record passed through all queries given in constructor.
 */
public class QueryFilter implements IFilter {

    /**
     * Queries list.
     */
    private List<ConditionalExpression> queries;

    /**
     * Basic constructor.
     *
     * @param queries
     */
    public QueryFilter(List<ConditionalExpression> queries) {
        this.queries = queries;
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expression : queries) {
            if (expression.getFieldGetter() == FieldValueGetters.FIRST_NAME) {
                if (!expression.getComparisonOperator().satisfied(record.getFirstName(), expression.getStringLiteral())) {
                    return false;
                }
            } else if (expression.getFieldGetter() == FieldValueGetters.LAST_NAME) {
                if (!expression.getComparisonOperator().satisfied(record.getLastName(), expression.getStringLiteral())) {
                    return false;
                }
            } else if (expression.getFieldGetter() == FieldValueGetters.JMBAG) {
                if (!expression.getComparisonOperator().satisfied(record.getJmbag(), expression.getStringLiteral())) {
                    return false;
                }
            } else {
                throw new RuntimeException("Nepoznati field getter!");
            }
        }

        return true;
    }
}
