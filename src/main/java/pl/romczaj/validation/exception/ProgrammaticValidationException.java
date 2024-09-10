package pl.romczaj.validation.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class ProgrammaticValidationException extends RuntimeException {

    private final Errors errors;

    public ProgrammaticValidationException(Errors errors) {
        this.errors = errors;
    }
}
