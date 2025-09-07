package uz.qodirov.exception;

import uz.qodirov.constant.StatusEnum;

public class UsernameAlreadyExistException extends ApiException {

    private static final long serialVersionUID = -4272462299434047895L;

    public UsernameAlreadyExistException() {
        super();
    }

    public UsernameAlreadyExistException(String message) {
        super(message);
    }

    public UsernameAlreadyExistException(StatusEnum status, Object data) {
        super(status, data);
    }
}