package hr.fer.zemris.java.custom.collections;

/**
 * This class represents empty stack exception. It is thrown when accesing
 * elements from empty stack.
 */
public class EmptyStackException extends RuntimeException {

    /**
     * Basic constructor.
     */
    public EmptyStackException() {
    }

    /**
     * Constructor with message which will be shown when exception is thrown.
     *
     * @param message Message that will be shown
     */
    public EmptyStackException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Message that will be shown
     * @param cause   Cause of exception
     */
    public EmptyStackException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause.
     *
     * @param cause Cause of exception
     */
    public EmptyStackException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message, cause and flag for enabling suppression and
     * stack trace.
     *
     * @param message            Message that will be shown
     * @param cause              Cause of exception
     * @param enableSuppression  Flag for suppression
     * @param writableStackTrace Flag for stack trace
     */
    public EmptyStackException(String message, Throwable cause, boolean
            enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
