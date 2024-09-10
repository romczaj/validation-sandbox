package pl.romczaj.validation.person.phone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneNumberAvailableValidator implements ConstraintValidator<PhoneNumberAvailable, String> {

    private final PhoneNumberRegistry phoneNumberRegistry;

    @Override
    public void initialize(PhoneNumberAvailable contactNumber) {

    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext cxt) {
        if (phoneNumber == null) {
            return true;
        }

        return phoneNumberRegistry.isAvailable(phoneNumber);
    }

}