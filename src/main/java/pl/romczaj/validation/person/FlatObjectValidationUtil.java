package pl.romczaj.validation.person;

import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
class FlatObjectValidationUtil {
    private final String field;
    private final String regex;
    private final Predicate<String> formatValidation;
    private final Predicate<String> valueValidation;

    ValidationResult validate() {
        if (field == null) {
            return ValidationResult.NULL_FIELD;
        }

        if (nonNull(regex) && !field.matches(regex)) {
            return ValidationResult.INCORRECT_FORMAT;
        }

        if (nonNull(formatValidation) && !formatValidation.test(field)) {
            return ValidationResult.INCORRECT_FORMAT;
        }
        if (nonNull(valueValidation) && !valueValidation.test(field)) {
            return ValidationResult.INCORRECT_VALUE;
        }
        return ValidationResult.CORRECT;
    }

}

    
