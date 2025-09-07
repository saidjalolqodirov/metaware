package uz.qodirov.exception;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ErrorResponse implements Serializable {
    private String path;
    private ErrorMessage message;
    private Object data;

    public ErrorResponse(String path, ErrorMessage message) {
        this.path = path;
        this.message = message;
    }
}