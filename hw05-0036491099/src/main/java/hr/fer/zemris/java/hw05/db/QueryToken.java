package hr.fer.zemris.java.hw05.db;

/**
 * This class represents token. Tokens are used as a basic unit of parsed
 * data in {@link QueryLexer}. Each token has
 * {@link QueryTokenType} and value.
 */
public class QueryToken {

    /**
     * Value of token.
     */
    private String value;

    /**
     * Type of token.
     */
    private QueryTokenType type;

    /**
     * Basic constructor.
     *
     * @param value Value of token
     * @param type  Type of token
     */
    public QueryToken(String value, QueryTokenType type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Getter for value.
     *
     * @return Value
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter for type
     *
     * @return type
     */
    public QueryTokenType getType() {
        return type;
    }
}
