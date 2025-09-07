package uz.qodirov.exception;

import lombok.Getter;
import lombok.Setter;
import uz.qodirov.generic.Status;

@Getter
@Setter
public class ErrorBetweenServicesException extends RuntimeException {
    private ErrorResponse errorResponse;
    private Status statusObject;
    private int httpStatus;

    public ErrorBetweenServicesException(ErrorResponse errorResponse, Status statusObject, int httpStatus) {
        this.errorResponse = errorResponse;
        this.statusObject = statusObject;
        this.httpStatus = httpStatus;
    }
}
