package uz.qodirov.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.qodirov.constant.StatusEnum;
import uz.qodirov.util.ErrorMessageUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class ErrorServiceImpl implements ErrorService {

    @Override
    public ErrorMessage apiExceptionHandling(ApiException e) {
        StatusEnum statusEnum = e.getStatus();
        String message = e.getMessage();
        return handling(statusEnum, message);
    }

    @Override
    public ErrorMessage exceptionHandling(Exception e) {
        StatusEnum statusEnum = StatusEnum.INTERNAL_SERVER_ERROR;
        String message = ErrorMessageUtil.getErrorMessage(e);
        return handling(statusEnum, message);
    }

    private ErrorMessage handling(StatusEnum statusEnum, String message) {
        log.info("generateErrorHandling code {}, message {}, status {}", statusEnum.getStatus().getCode(), message, statusEnum);
        return new ErrorMessage(statusEnum.getMessage(), statusEnum.getMessage(), statusEnum.getMessage());
    }

}
