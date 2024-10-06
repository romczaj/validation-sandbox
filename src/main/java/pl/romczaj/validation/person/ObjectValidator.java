package pl.romczaj.validation.person;

import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
class ObjectValidator<T> {
    private final T field;
    private final String regex;
    private final Predicate<T> formatValidation;
    private final Predicate<T> valueValidation;

    static <T> ObjectValidator<T> of(T field, String regex, Predicate<T> formatValidation, Predicate<T> valueValidation) {
        return new ObjectValidator<>(field, regex, formatValidation, valueValidation);
    }

    static <T> ObjectValidator<T> onlyNonNull(T field) {
        return new ObjectValidator<>(field, null, null, null);
    }

    ValidationResult validate() {

        if (field == null) {
            return ValidationResult.NULL_FIELD;
        }

        if (nonNull(regex) && field instanceof String string && !string.matches(regex)) {
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

    
