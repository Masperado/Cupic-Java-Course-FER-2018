package hr.fer.zemris.java.gui.layout;

/**
 * This method represents Calc layout exception which is throwed once error occurs in {@link CalcLayout}.
 */
public class CalcLayoutException extends RuntimeException {

    /**
     * Basic constructor.
     */
    public CalcLayoutException() {
    }

    /**
     * Constructor with message.
     *
     * @param message Message
     */
    public CalcLayoutException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Message
     * @param cause   Cause
     */
    public CalcLayoutException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause
     *
     * @param cause Cause
     */
    public CalcLayoutException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with message, cause, enableSuppression flag and writableStackTrace flag.
     *
     * @param message            Message
     * @param cause              Cause
     * @param enableSuppression  EnableSuppression flag
     * @param writableStackTrace WritableStackTrace flag
     */
    public CalcLayoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
