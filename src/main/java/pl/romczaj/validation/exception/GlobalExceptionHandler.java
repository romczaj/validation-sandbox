package pl.romczaj.validation.exception;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.romczaj.validation.exception.ApiErrorResponse.ApiError;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String MESSAGE = "Exception handler";

    @ExceptionHandler(ProgrammaticValidationException.class)
    public ResponseEntity<ApiErrorResponse> handleApplicationException(ProgrammaticValidationException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.badRequest(ex.getErrors());
        return apiErrorResponse.toResponseEntity();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(BindException ex) {

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.badRequest(ex.getBindingResult());
//
//        List<String> fieldErrors = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).toList();
//        List<String> objectErrors = ex.getBindingResult().getGlobalErrors().stream().map(e -> e.getObjectName() + ": " + e.getDefaultMessage()).toList();
//        String error = Stream.of(fieldErrors, objectErrors).flatMap(Collection::stream).collect(Collectors.joining(", "));
//        ApiErrorResponse apiErrorResponse = ApiErrorResponse.from(HttpStatus.BAD_REQUEST, error);
//
//        ex.getBindingResult().getFieldError();

        return apiErrorResponse.toResponseEntity();
    }


}
