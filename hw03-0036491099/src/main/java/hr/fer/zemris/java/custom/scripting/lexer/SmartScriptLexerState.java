package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This enumeration represents state of {@link SmartScriptLexer}.
 */
public enum SmartScriptLexerState {

    /**
     * State when inside text
     */
    TEXT,

    /**
     * State when inside tag
     */
    TAG
}
