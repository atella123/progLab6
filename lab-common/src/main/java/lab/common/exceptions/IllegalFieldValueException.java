package lab.common.exceptions;

public class IllegalFieldValueException extends RuntimeException {

    public IllegalFieldValueException() {
    }

    public IllegalFieldValueException(String message) {
        super(message);
    }

    public IllegalFieldValueException(Throwable cause) {
        super(cause);
    }

    public IllegalFieldValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalFieldValueException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
