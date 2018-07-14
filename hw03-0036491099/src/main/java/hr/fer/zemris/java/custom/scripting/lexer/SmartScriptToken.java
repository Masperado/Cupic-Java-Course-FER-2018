package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class represents token. Tokens are used as a basic unit of parsed
 * data in {@link SmartScriptLexer}. Each token has
 * {@link SmartScriptTokenType} and value.
 */
public class SmartScriptToken {

    /**
     * Type of token.
     */
    private SmartScriptTokenType type;

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
    public SmartScriptToken(SmartScriptTokenType type, Object value) {
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
    public SmartScriptTokenType getType() {
        return type;
    }
}