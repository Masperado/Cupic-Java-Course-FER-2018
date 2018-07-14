package hr.fer.zemris.java.hw03.prob1;

/**
 * This enumeration represents state of {@link Lexer}.
 */
public enum LexerState {

    /**
     * Basic lexer state.
     */
    BASIC,

    /**
     * Extended lexer state (inside tag)
     */
    EXTENDED
}
