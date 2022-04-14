package lab.common.exceptions;

public class NoParserAvailableException extends RuntimeException {

    public NoParserAvailableException() {
    }

    public NoParserAvailableException(String message) {
        super(message);
    }

    public NoParserAvailableException(Throwable cause) {
        super(cause);
    }

    public NoParserAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoParserAvailableException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
