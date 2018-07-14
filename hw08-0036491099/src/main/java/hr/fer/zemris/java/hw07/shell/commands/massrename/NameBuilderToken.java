package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * This class represents token for {@link NameBuilderLexer}.
 */
public class NameBuilderToken {

    /**
     * Value of token.
     */
    private String value;

    /**
     * Type of token.
     */
    private NameBuilderTokenType type;

    /**
     * Basic constructor.
     *
     * @param value Value of token
     * @param type  Type of token
     */
    public NameBuilderToken(String value, NameBuilderTokenType type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Getter for value.
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter for type.
     *
     * @return type
     */
    public NameBuilderTokenType getType() {
        return type;
    }
}
