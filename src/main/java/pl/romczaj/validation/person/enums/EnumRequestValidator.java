package pl.romczaj.validation.person.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

class EnumRequestValidator implements ConstraintValidator<EnumConstraint, String> {

    private List<String> enumStrings;

    @Override
    public void initialize(EnumConstraint enumConstraint) {
        Class<? extends Enum<?>> aClass = enumConstraint.value();

        Enum<?>[] enumValues = aClass.getEnumConstants();

        enumStrings = Arrays.stream(enumValues)
            .map(Enum::name)
            .toList();

    }

    @Override
    public boolean isValid(String enumString, ConstraintValidatorContext context) {

        if (enumString == null || enumStrings.contains(enumString)) {
            return true;
        }

        context
            .buildConstraintViolationWithTemplate(String.format("Available values %s", enumStrings))
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        return false;
    }
}
