package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class represents Lexer exception. It is thrown when exception occurs
 * during lexing in {@link SmartScriptLexer}.
 */
public class SmartScriptLexerException extends RuntimeException {

    /**
     * Basic constructor.
     */
    public SmartScriptLexerException() {
    }

    /**
     * Constructor with message.
     *
     * @param message Message
     */
    public SmartScriptLexerException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Message
     * @param cause   Cause
     */
    public SmartScriptLexerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause.
     *
     * @param cause Cause
     */
    public SmartScriptLexerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message, cause, flag for enableSupression and flag
     * for writableStackTrace.
     *
     * @param message            Message
     * @param cause              Cause
     * @param enableSuppression  Flag for enableSuperession
     * @param writableStackTrace FLag for writable stack trace
     */
    public SmartScriptLexerException(String message, Throwable cause, boolean
            enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
