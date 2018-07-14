package hr.fer.zemris.java.hw07.shell;

/**
 * This class represents Shell input output exception. Is is thrown when exception in reading or writing to {@link Environment} occurs.
 */
public class ShellIOException extends RuntimeException {

    /**
     * Basic constructor.
     */
    public ShellIOException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message Message
     */
    public ShellIOException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Message
     * @param cause   Cause
     */
    public ShellIOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause.
     *
     * @param cause Cause
     */
    public ShellIOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message, cause, enableSuppression flag and writableStackTrace flag.
     *
     * @param message            Message
     * @param cause              Cause
     * @param enableSuppression  Enable supression flag
     * @param writableStackTrace writable stack trace flag
     */
    protected ShellIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
