package uz.qodirov.exception;

import lombok.*;
import uz.qodirov.generic.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorMessageResponse {
    private Status status;
    private ErrorMessage message;
    private ProcErrorResponse procErrorResponse;
}
