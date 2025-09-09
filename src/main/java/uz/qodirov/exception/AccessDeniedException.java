package uz.qodirov.exception;

import uz.qodirov.constant.StatusEnum;

public class AccessDeniedException extends ApiException {

    public AccessDeniedException(StatusEnum status, Object data) {
        super(status, data);
    }

}
