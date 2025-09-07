package uz.qodirov.generic;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.ResponseEntity;

@Data
@ToString
public class GenericSuccessResponse<T> {

    T data;

    public GenericSuccessResponse(T data) {
        this.data = data;
    }

    public GenericSuccessResponse() {
        this.data = null;
    }

    public static <T> ResponseEntity<GenericSuccessResponse<T>> ok(T t) {
        return ResponseEntity.ok(new GenericSuccessResponse<>(t));
    }

    public static <T> ResponseEntity<GenericSuccessResponse<T>> ok() {
        return ResponseEntity.ok(new GenericSuccessResponse<>());
    }
}
