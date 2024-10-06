package pl.romczaj.validation.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ProgrammaticValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleApplicationException(ProgrammaticValidationException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.badRequest(ex.getErrors());
        return apiErrorResponse.toResponseEntity();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(BindException ex) {

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.badRequest(ex.getBindingResult());
        return apiErrorResponse.toResponseEntity();
    }
}
