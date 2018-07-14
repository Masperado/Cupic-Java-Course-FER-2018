package hr.fer.zemris.java.custom.scripting.parser;

/**
 * This class represents Lexer exception. It is thrown when exception occurs
 * during parsing in {@link SmartScriptParser}.
 */
public class SmartScriptParserException extends RuntimeException {

    /**
     * Basic constructor.
     */
    public SmartScriptParserException() {
    }


    /**
     * Constructor with message.
     *
     * @param message Message
     */
    public SmartScriptParserException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Message
     * @param cause   Cause
     */
    public SmartScriptParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause.
     *
     * @param cause Cause
     */
    public SmartScriptParserException(Throwable cause) {
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
    public SmartScriptParserException(String message, Throwable cause,
                                      boolean enableSuppression, boolean
                                              writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
