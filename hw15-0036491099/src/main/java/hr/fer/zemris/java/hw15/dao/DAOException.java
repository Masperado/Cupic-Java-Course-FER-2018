package hr.fer.zemris.java.hw15.dao;

/**
 * This class represents DAO exception. It is thrown when exception occurs while communication with database.
 */
public class DAOException extends RuntimeException {


    /**
     * Basic constructor.
     */
    public DAOException() {
    }

    /**
     * Constructor with message, cause, enableSupression flag and writableStackTrace flag.
     *
     * @param message            Message
     * @param cause              Cause
     * @param enableSuppression  EnableSupression flag
     * @param writableStackTrace WritableStackTrace flag
     */
    public DAOException(String message, Throwable cause,
                        boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Message
     * @param cause   Cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with message.
     *
     * @param message Message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructor with cause.
     *
     * @param cause Cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
}