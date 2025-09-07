package uz.qodirov.exception;

public class InvalidDataException extends ApiException {
    private static final long serialVersionUID = 1226481108563037071L;

    public InvalidDataException(String message) {
        super(message);
    }

}
