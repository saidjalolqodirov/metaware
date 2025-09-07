package uz.qodirov.exception;


public interface ErrorService {
    ErrorMessage apiExceptionHandling(ApiException e);

    ErrorMessage exceptionHandling(Exception e);
}
