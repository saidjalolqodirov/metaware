package uz.qodirov.exception;

import uz.qodirov.constant.StatusEnum;

public class DataNotFoundException extends ApiException {

    private static final long serialVersionUID = -4272462299434047895L;

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(StatusEnum status, Object data) {
        super(status, data);
    }
}