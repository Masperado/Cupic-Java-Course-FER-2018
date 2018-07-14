package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents Lexer exception. It is thrown when exception occurs
 * during lexerization.
 */
public class LexerException extends RuntimeException {

    /**
     * Basic constructor.
     */
    public LexerException() {
    }

    /**
     * Constructor with message.
     *
     * @param message Message
     */
    public LexerException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Message
     * @param cause   Cause
     */
    public LexerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause.
     *
     * @param cause Cause
     */
    public LexerException(Throwable cause) {
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
    public LexerException(String message, Throwable cause, boolean
            enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
