package uz.qodirov.generic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
@JsonPropertyOrder({"status", "error", "procError"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericErrorResponse<T, E> extends GenericDto {
    T error;
    E procError;
    Status status;

    public GenericErrorResponse(Status status) {
        this.error = null;
        this.procError = null;
        this.status = status;
    }


    public GenericErrorResponse(T errorResponse, Status status) {
        this.error = errorResponse;
        this.status = status;
    }

    public GenericErrorResponse(T errorResponse, E procErrorResponse, Status status) {
        this.error = errorResponse;
        this.procError = procErrorResponse;
        this.status = status;
    }

    public static <T, E> ResponseEntity<GenericErrorResponse<T, E>> error(int httpStatus, T t, Status status) {
        return ResponseEntity.status(httpStatus).body(new GenericErrorResponse<>(t, status));
    }

    public static <T, E> ResponseEntity<GenericErrorResponse<T, E>> error(HttpStatus httpStatus, Status status) {
        return ResponseEntity.status(httpStatus).body(new GenericErrorResponse<>(status));
    }

    public static <T, E> ResponseEntity<GenericErrorResponse<T, E>> error(int httpStatus, Status status) {
        return ResponseEntity.status(httpStatus).body(new GenericErrorResponse<>(status));
    }

    /* ----------------------------- -------------- --------------------------*/

    public static <T, E> ResponseEntity<GenericErrorResponse<T, E>> error(HttpStatus httpStatus, T t, E e, Status status) {
        return ResponseEntity.status(httpStatus).body(new GenericErrorResponse<>(t, e, status));
    }

    public static <T, E> ResponseEntity<GenericErrorResponse<T, E>> error(int httpStatus, T t, E e, Status status) {
        return ResponseEntity.status(httpStatus).body(new GenericErrorResponse<>(t, e, status));
    }


}
