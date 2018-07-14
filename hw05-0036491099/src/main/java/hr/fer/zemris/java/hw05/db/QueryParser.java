package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents QueryParser. It is used for parsing queries. It parses given query into {@link List}
 * of {@link ConditionalExpression}.
 */
public class QueryParser {

    /**
     * Lexer for generating tokens from text.
     */
    private QueryLexer lexer;

    /**
     * List of parsed expressions.
     */
    private List<ConditionalExpression> queries;

    /**
     * Basic constructor.
     *
     * @param data Query text
     */
    public QueryParser(String data) {
        this.lexer = new QueryLexer(data);
        this.queries = new ArrayList<>();
        parse();
    }

    /**
     * This method check if query given in constructor is direct.
     *
     * @return True if it is, false otherwise
     */
    public boolean isDirectQuery() {
        if (queries.size() != 1) {
            return false;
        }

        ConditionalExpression expression = queries.get(0);

        return expression.getFieldGetter() == FieldValueGetters.JMBAG && expression.getComparisonOperator() == ComparisonOperators.EQUALS;
    }

    /**
     * This method is used for getting jmbag of direct query.
     *
     * @return JMBAG of given query
     * @throws IllegalStateException If query is not direct
     */
    public String getQueriedJmbag() {
        if (!isDirectQuery()) {
            throw new IllegalStateException("Query nije direktan!");
        }

        return queries.get(0).getStringLiteral();
    }

    /**
     * This method is used for getting list of {@link ConditionalExpression} parsed for query given in constructor.
     *
     * @return List of {@link ConditionalExpression}
     */
    public List<ConditionalExpression> getQuery() {
        return queries;
    }

    /**
     * This method is used for parsing query given in constructor into {@link List} of {@link ConditionalExpression}.
     */
    private void parse() {

        QueryToken token = lexer.nextToken();

        while (true) {
            if (token.getType() != QueryTokenType.FIELD) {
                throw new RuntimeException("Prvi token u expressionu nije field!");
            }
            IFieldValueGetter getter = parseGetter(token);

            token = lexer.nextToken();

            if (token.getType() != QueryTokenType.OPERATOR) {
                throw new RuntimeException("Drugi token u expressionu nije field!");
            }

            IComparisonOperator operator = parseOperator(token);

            token = lexer.nextToken();

            if (token.getType() != QueryTokenType.STRING) {
                throw new RuntimeException("Treći token u expressionu nije string literal!");
            }

            String stringLiteral = token.getValue().substring(1, token.getValue().length() - 1);

            queries.add(new ConditionalExpression(getter, stringLiteral, operator));

            token = lexer.nextToken();

            if (token.getType() == QueryTokenType.AND) {
                token = lexer.nextToken();
            } else if (token.getType() == QueryTokenType.EOF) {
                break;
            } else {
                throw new RuntimeException("Ulaz se ne može parsirati!");
            }

        }
    }

    /**
     * This method is used for parsing {@link QueryToken} into {@link IComparisonOperator}.
     *
     * @param token {@link QueryToken}
     * @return Parsed {@link IComparisonOperator}
     */
    private IComparisonOperator parseOperator(QueryToken token) {
        switch (token.getValue()) {
            case ">":
                return ComparisonOperators.GREATER;
            case ">=":
                return ComparisonOperators.GREATER_OR_EQUALS;
            case "<":
                return ComparisonOperators.LESS;
            case "<=":
                return ComparisonOperators.LESS_OR_EQUALS;
            case "=":
                return ComparisonOperators.EQUALS;
            case "!=":
                return ComparisonOperators.NOT_EQUALS;
            case "LIKE":
                return ComparisonOperators.LIKE;
            default:
                throw new RuntimeException("Nepoznati operator!");
        }
    }

    /**
     * This method is used for parsing {@link QueryToken} into {@link IFieldValueGetter}.
     *
     * @param token {@link QueryToken}
     * @return Parsed {@link IFieldValueGetter}
     */
    private IFieldValueGetter parseGetter(QueryToken token) {
        switch (token.getValue()) {
            case "firstName":
                return FieldValueGetters.FIRST_NAME;
            case "lastName":
                return FieldValueGetters.LAST_NAME;
            case "jmbag":
                return FieldValueGetters.JMBAG;
            default:
                throw new RuntimeException("Nepoznati getter!");
        }
    }
}
