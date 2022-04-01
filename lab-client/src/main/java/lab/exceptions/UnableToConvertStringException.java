package lab.exceptions;

public class UnableToConvertStringException extends IllegalArgumentException {

    public UnableToConvertStringException() {
    }

    public UnableToConvertStringException(String s) {
        super(s);
    }

    public UnableToConvertStringException(Throwable cause) {
        super(cause);
    }

    public UnableToConvertStringException(String message, Throwable cause) {
        super(message, cause);
    }
}
