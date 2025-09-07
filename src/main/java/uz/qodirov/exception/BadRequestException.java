package uz.qodirov.exception;

import uz.qodirov.constant.StatusEnum;

public class BadRequestException extends ApiException {

    private static final long serialVersionUID = -5124462299434045687L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(StatusEnum status, Object data) {
        super(status, data);
    }
}