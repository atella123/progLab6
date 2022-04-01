package lab.exceptions;

public class StringIsNullException extends RuntimeException {

    public StringIsNullException() {
    }

    public StringIsNullException(String message) {
        super(message);
    }

    public StringIsNullException(Throwable cause) {
        super(cause);
    }

    public StringIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public StringIsNullException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
