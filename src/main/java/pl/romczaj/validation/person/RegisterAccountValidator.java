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
        log.info("supports called");
        return RegisterAccountRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        log.info("validate called");
        RegisterAccountRequest request = (RegisterAccountRequest) target;

        boolean isEmailAlreadyUsed = emailRegistry.isAlreadyUsed(request.email());
        if (isEmailAlreadyUsed) {
            errors.reject("ER_001", "Email already used");
            throw new ProgrammaticValidationException(errors);
        }

        boolean emailFormatValid = EmailValidator.getInstance().isValid(request.email());
        if (!emailFormatValid) {
            rejectIfEmptyOrWhitespace(errors, "email", "ER_01", "Email invalid format");
        }

        rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty", "Nie może być puste");

        PersonAddress personAddress = request.personAddress();

        //ValidationCommand<PersonAddress> personAddressValidationCommand = new ValidationCommand<>()

        if (personAddress == null) {
            errors.rejectValue("personAddress", "asds", null, "address not null");
        } else {
            rejectIfEmptyOrWhitespace(errors, "personAddress.city", "NotEmpty");
            rejectIfEmptyOrWhitespace(errors, "personAddress.street", "NotEmpty");
        }

        FlatObjectValidationUtil emailAddressValidation = new FlatObjectValidationUtil(
            request.email(), null, EmailValidator.getInstance()::isValid, null);
        ValidationResult emailValidationResult = emailAddressValidation.validate();

        switch (emailValidationResult){
            case NULL_FIELD -> errors.rejectValue("email", "NOT_NULL");
            case INCORRECT_FORMAT -> errors.rejectValue("email", "INCORRECT_FORMAT");
            case INCORRECT_VALUE -> errors.rejectValue("email", "INCORRECT_VALUE", "Email is already used");
            case CORRECT -> {}
        }

        FlatObjectValidationUtil phoneNumberValidation = new FlatObjectValidationUtil(
            request.phoneNumber(), "\\d+", null, phoneNumberRegistry::isAvailable);
        ValidationResult validate = phoneNumberValidation.validate();

        switch (validate) {
            case NULL_FIELD -> errors.rejectValue("phoneNumber", "NOT_NULL");
            case INCORRECT_FORMAT -> errors.rejectValue("phoneNumber", "INCORRECT_FORMAT");
            case INCORRECT_VALUE -> errors.rejectValue("phoneNumber", "INCORRECT_VALUE", "Phone number is already used");
            case CORRECT -> {}
        }

//        if (phoneNumber == null) {
//            errors.rejectValue("phoneNumber", "ERR5", null, "phone number not null");
//        } else {
//            boolean phoneNumberAvailable = phoneNumberRegistry.isAvailable(phoneNumber);
//            if (!phoneNumberAvailable) {
//                errors.rejectValue("phoneNumber", "ERR6", null, "phone number is not available");
//            }
//        }

        if(errors.hasErrors()){
            throw new ProgrammaticValidationException(errors);
        }

        // Add additional validation logic
        // Example:
        // if (user.getUsername().length() < 5) {
        //     errors.rejectValue("username", "Size.userForm.username");
        // }
    }
}