package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This enumeration represents {@link SmartScriptToken} type.
 */
public enum SmartScriptTokenType {

    /**
     * End of File type.
     */
    EOF,

    /**
     * Text type.
     */
    TEXT,

    /**
     * Integer tyoe.
     */
    INTEGER,

    /**
     * Double type.
     */
    DOUBLE,

    /**
     * Begin tag type.
     */
    BEGINTAG,

    /**
     * End tag type.
     */
    ENDTAG
}
