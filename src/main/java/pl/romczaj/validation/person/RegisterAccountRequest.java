package pl.romczaj.validation.person;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import pl.romczaj.validation.person.email.EmailAvailable;
import pl.romczaj.validation.person.enums.EnumConstraint;
import pl.romczaj.validation.person.phone.PhoneNumberAvailable;

public record RegisterAccountRequest(
    @NotEmpty String firstName,
    @NotEmpty String lastName,
    @NotNull @Email @EmailAvailable String email,
    @NotNull @Valid PersonAddress personAddress,
    @NotNull @Pattern(regexp = "\\d{9}") @PhoneNumberAvailable String phoneNumber,
    @NotNull @EnumConstraint(PersonType.class) String personType
) {

    record PersonAddress(
        @NotEmpty String city,
        @NotEmpty String street
    ) {
    }
}
