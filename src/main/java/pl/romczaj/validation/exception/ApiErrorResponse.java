package pl.romczaj.validation.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public record ApiErrorResponse(
    List<ApiError> errors,
    HttpStatus status
) {

    public record ApiError(
        String field,
        String code,
        String message
    ) {
    }

    public static ApiErrorResponse badRequest(BindingResult bindingResult) {
        List<ApiError> apiErrors = bindingResult.getFieldErrors().stream()
            .map(e ->
                new ApiError(
                    e.getField(),
                    e.getCode(),
                    e.getDefaultMessage()
                ))
            .toList();
        return new ApiErrorResponse(
            apiErrors,
            BAD_REQUEST
        );
    }

    public static ApiErrorResponse badRequest(Errors errors) {
        List<ApiError> apiErrors = errors.getFieldErrors().stream()
            .map(e ->
                new ApiError(
                    e.getField(),
                    e.getCode(),
                    e.getDefaultMessage()
                ))
            .toList();

        return new ApiErrorResponse(
            apiErrors,
            BAD_REQUEST
        );
    }

    public ResponseEntity<ApiErrorResponse> toResponseEntity() {
        return ResponseEntity.status(status).body(this);
    }

}
