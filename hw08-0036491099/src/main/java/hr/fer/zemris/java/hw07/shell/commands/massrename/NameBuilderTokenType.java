package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * This enumeration represents token type for {@link NameBuilderToken}.
 */
public enum NameBuilderTokenType {

    /**
     * String type
     */
    STRING,

    /**
     * Begging command type
     */
    BEGINCOMMAND,

    /**
     * End command type
     */
    ENDCOMMAND,

    /**
     * Number type
     */
    NUMBER,

    /**
     * Comma type
     */
    COMMA,

    /**
     * End of file type
     */
    EOF
}
