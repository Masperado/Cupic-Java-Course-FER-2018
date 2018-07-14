package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents token. Tokens are used as a basic unit of parsed
 * data in {@link Lexer}. Each token has {@link TokenType} and value.
 */
public class Token {

    /**
     * Type of token.
     */
    private TokenType type;

    /**
     * Value of token.
     */
    private Object value;

    /**
     * Basic constructor.
     *
     * @param type  Type of token
     * @param value Value of token
     */
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Getter for value.
     *
     * @return Token value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Getter for type.
     *
     * @return Token type
     */
    public TokenType getType() {
        return type;
    }
}