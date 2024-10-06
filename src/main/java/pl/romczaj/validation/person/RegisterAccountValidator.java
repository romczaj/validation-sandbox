package pl.romczaj.validation.person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.romczaj.validation.exception.ProgrammaticValidationException;
import pl.romczaj.validation.person.RegisterAccountRequest.PersonAddress;
import pl.romczaj.validation.person.email.EmailRegistry;
import pl.romczaj.validation.person.phone.PhoneNumberRegistry;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

@RequiredArgsConstructor
@Component
@Slf4j
public class RegisterAccountValidator implements Validator {

    private final EmailRegistry emailRegistry;
    private final PhoneNumberRegistry phoneNumberRegistry;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterAccountRequestProgrammatic.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterAccountRequestProgrammatic request = (RegisterAccountRequestProgrammatic) target;

        validateEmail(request, errors);
        validatePhoneNumber(request, errors);
        validatePersonAddress(request, errors);

        rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty", "Nie może być puste");

        if (errors.hasErrors()) {
            throw new ProgrammaticValidationException(errors);
        }
    }

    private void validateEmail(RegisterAccountRequestProgrammatic request, Errors errors) {
        ValidationResult emailValidationResult = ObjectValidator.of(
                request.email(), null, EmailValidator.getInstance()::isValid, emailRegistry::isAvailable)
            .validate();

        switch (emailValidationResult) {
            case NULL_FIELD -> errors.rejectValue("email", "NOT_NULL");
            case INCORRECT_FORMAT -> errors.rejectValue("email", "INCORRECT_FORMAT");
            case INCORRECT_VALUE ->  errors.rejectValue("email", "INCORRECT_VALUE", "Email is already used");
            case CORRECT -> {}
        }
    }

    private void validatePhoneNumber(RegisterAccountRequestProgrammatic request, Errors errors) {
        ValidationResult validationResult = ObjectValidator.of(
                request.phoneNumber(), "\\d{9}", null, phoneNumberRegistry::isAvailable)
            .validate();

        switch (validationResult) {
            case NULL_FIELD -> errors.rejectValue("phoneNumber", "NOT_NULL");
            case INCORRECT_FORMAT -> errors.rejectValue("phoneNumber", "INCORRECT_FORMAT");
            case INCORRECT_VALUE -> errors.rejectValue("phoneNumber", "INCORRECT_VALUE", "Phone number is already used");
            case CORRECT -> {}
        }
    }

    private void validatePersonAddress(RegisterAccountRequestProgrammatic request, Errors errors) {
        PersonAddress personAddress = request.personAddress();
        ValidationResult validationResult = ObjectValidator.onlyNonNull(personAddress).validate();
        switch (validationResult) {
            case NULL_FIELD -> errors.rejectValue("personAddress", "NOT_NULL");
            case CORRECT -> {
                rejectIfEmptyOrWhitespace(errors, "personAddress.city", "NotEmpty");
                rejectIfEmptyOrWhitespace(errors, "personAddress.street", "NotEmpty");
            }
            default -> {}
        }
    }
}