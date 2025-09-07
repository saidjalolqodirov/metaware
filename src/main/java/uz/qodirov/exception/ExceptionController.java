package uz.qodirov.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import uz.qodirov.constant.StatusEnum;
import uz.qodirov.generic.GenericErrorResponse;
import uz.qodirov.util.ErrorMessageUtil;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class ExceptionController {

    private final ErrorService errorService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericErrorResponse<ErrorResponse, ProcErrorResponse>> validationExceptions(MethodArgumentNotValidException ex, ServletWebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return GenericErrorResponse.error(
                StatusEnum.VALIDATION_ERROR.getHttpStatus(),
                new ErrorResponse(
                        webRequest.getRequest().getRequestURI(),
                        ErrorMessage
                                .builder()
                                .uz(errors.toString())
                                .ru(errors.toString())
                                .en(errors.toString())
                                .build()),
                StatusEnum.VALIDATION_ERROR.getStatus()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericErrorResponse<ErrorResponse, Object>> accessDeniedException(AccessDeniedException e, ServletWebRequest webRequest) {
        log.error("Access denied Exception.\nError message: {}\nWebRequest: {}", ErrorMessageUtil.getErrorMessage(e), webRequest);
        StatusEnum statusEnum = StatusEnum.FORBIDDEN;
        return GenericErrorResponse.error(
                statusEnum.getHttpStatus(),
                ErrorResponse.builder()
                        .path(webRequest.getRequest().getRequestURI())
                        .message(new ErrorMessage(statusEnum.getMessage(), statusEnum.getMessage(), statusEnum.getMessage()))
                        .data(new HashMap<>() {{
                            put("warning", "PERMISSION BIRIKTIRILMAGAN!!!");
                        }})
                        .build(),
                statusEnum.getStatus()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("HttpMessageNotReadableException e: {}", String.valueOf(e));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Void> methodNotAllowed(HttpRequestMethodNotSupportedException e) {
        log.error("Request method not supported error  {}", e.getMethod());
        return ResponseEntity
                .status(StatusEnum.METHOD_NOT_ALLOWED.getHttpStatus())
                .build();

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericErrorResponse<ErrorResponse, Object>> unknownError(Exception e, ServletWebRequest webRequest) {
        String exceptionMessage = ErrorMessageUtil.getErrorMessage(e);
        log.error("Exception controller handling unknown exception. Error message: {}", exceptionMessage);
        StatusEnum statusEnum = StatusEnum.INTERNAL_SERVER_ERROR;
        return GenericErrorResponse.error(
                statusEnum.getHttpStatus(),
                ErrorResponse.builder()
                        .path(webRequest.getRequest().getRequestURI())
                        .message(errorService.exceptionHandling(e))
                        .data(new HashMap<>() {{
                            put("exception_message", exceptionMessage);
                        }})
                        .build(),
                statusEnum.getStatus()
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GenericErrorResponse<ErrorResponse, ProcErrorResponse>> missingServletRequestParameterExceptionException(MissingServletRequestParameterException e, ServletWebRequest webRequest) {
        log.error("Card parameter validation  error ", e);
        StatusEnum statusEnum = StatusEnum.PARAMETER_VALIDATION_ERROR;
        String message = e.getParameterName() + " " + statusEnum.getMessage();
        return GenericErrorResponse.error(
                statusEnum.getHttpStatus(),
                new ErrorResponse(
                        webRequest.getRequest().getRequestURI(),
                        ErrorMessage.builder()
                                .uz(message)
                                .ru(message)
                                .en(message)
                                .build()),
                statusEnum.getStatus());
    }

    @ExceptionHandler(ErrorBetweenServicesException.class)
    @ApiResponses(value = {@ApiResponse(description = "Between services exception")})
    public ResponseEntity<GenericErrorResponse<ErrorResponse, ProcErrorResponse>> betweenServicesException(
            ErrorBetweenServicesException e,
            ServletWebRequest webRequest
    ) {


        log.info("Between services exception http status {} errorMessage {}  statusObject {}", e.getHttpStatus(), e.getErrorResponse().getMessage(), e.getStatusObject());

        return GenericErrorResponse.error(
                e.getHttpStatus(),
                new ErrorResponse(
                        webRequest.getRequest().getRequestURI(),
                        e.getErrorResponse().getMessage()),
                new ProcErrorResponse(null, null),
                e.getStatusObject()
        );
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ApiResponses(value = {@ApiResponse(description = "Api exception")})
    public ResponseEntity<GenericErrorResponse<ErrorResponse, ProcErrorResponse>> apiException(HttpClientErrorException e, ServletWebRequest webRequest) {
        log.error("Http ClientError Exception message: {} ", e.getMessage());
        String errorMessage = ErrorMessageUtil.getErrorMessage(e.getResponseBodyAsString());
        return GenericErrorResponse.error(
                StatusEnum.HTTP_CLIENT_ERROR.getHttpStatus(),
                ErrorResponse.builder()
                        .path(webRequest.getRequest().getRequestURI())
                        .message(ErrorMessage
                                .builder()
                                .uz(errorMessage)
                                .ru(errorMessage)
                                .en(errorMessage)
                                .build())
                        .build(),
                StatusEnum.HTTP_CLIENT_ERROR.getStatus()
        );
    }

    @ExceptionHandler(ApiException.class)
    @ApiResponses(value = {@ApiResponse(description = "Api exception")})
    public ResponseEntity<GenericErrorResponse<ErrorResponse, ProcErrorResponse>> apiException(ApiException e, ServletWebRequest webRequest) {
        StatusEnum statusEnum = e.getStatus();
        log.error("Api exception code {}  message {} status {}", e.getCode(), e.getMessage(), statusEnum);
        ErrorMessage errorMessage = errorService.apiExceptionHandling(e);
        return GenericErrorResponse.error(
                statusEnum.getHttpStatus(),
                ErrorResponse.builder()
                        .path(webRequest.getRequest().getRequestURI())
                        .message(errorMessage)
                        .data(e.getData())
                        .build(),
                statusEnum.getStatus()
        );
    }

}
