package lab.common.exceptions;

public class UnableToConvertValueException extends IllegalArgumentException {

    public UnableToConvertValueException() {
    }

    public UnableToConvertValueException(String s) {
        super(s);
    }

    public UnableToConvertValueException(Throwable cause) {
        super(cause);
    }

    public UnableToConvertValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
