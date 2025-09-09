package uz.qodirov.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import uz.qodirov.generic.Status;

@Slf4j
@Getter
@AllArgsConstructor
public enum StatusEnum {
    VALIDATION_ERROR(new Status("___001", "Bad Request"), 400),
    FORBIDDEN(new Status("___002", "Forbidden"), 403),
    INTERNAL_SERVER_ERROR(new Status("___003", "Internal server error"), 500),
    DATA_NOT_FOUND(new Status("___004", "Data not found"), 404),
    UNAUTHORIZED(new Status("___005", "Unauthorized"), 401),
    HTTP_CLIENT_ERROR(new Status("___006", "Http client error"), 500),
    METHOD_NOT_ALLOWED(new Status("___007", "Method Not Allowed"), 405),
    PARAMETER_VALIDATION_ERROR(new Status("___008", "request param is mandatory"), 422),
    CONNECTION_TIME_OUT(new Status("___009", "Connection time out"), 422),

    USERNAME_ALREADY_EXIST(new Status("110101", "username already exist"), 422),
    EMAIL_ALREADY_EXIST(new Status("110102", "email already exist"), 422),
    USERNAME_OR_PASSWORD_NOT_MATCH(new Status("110103", "username or password not match"), 422),
    TOKEN_EXPIRED(new Status("110104", "token expired"), 422),
    USER_NOT_FOUND(new Status("110105", "user not found"), 422),

    ;
    private final Status status;
    private final int httpStatus;

    public String getCode() {
        return status.getCode();
    }

    public String getMessage() {
        return status.getMessage();
    }

}
