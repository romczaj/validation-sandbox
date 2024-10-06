package pl.romczaj.validation.person.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EmailAvailableValidator implements ConstraintValidator<EmailAvailable, String> {

    private final EmailRegistry emailRegistry;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (email == null) {
            return true;
        }

        return emailRegistry.isAvailable(email);
    }
}
