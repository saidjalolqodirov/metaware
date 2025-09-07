package uz.qodirov.exception;

import lombok.Getter;
import uz.qodirov.constant.StatusEnum;

import java.io.Serial;

@Getter
public class ApiException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6399880247446892826L;

    private String code;
    protected StatusEnum status;
    protected Object data;


    public ApiException() {
    }

    public ApiException(StatusEnum status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
    }

}
