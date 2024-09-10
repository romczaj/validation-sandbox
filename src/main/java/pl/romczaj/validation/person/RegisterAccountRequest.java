package pl.romczaj.validation.person;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pl.romczaj.validation.person.enums.EnumConstraint;
import pl.romczaj.validation.person.phone.PhoneNumberAvailable;

record RegisterAccountRequest(
    @NotEmpty String firstName,
    @NotEmpty String lastName,
    @NotNull @Email String email,
    @NotNull @Valid PersonAddress personAddress,
    @NotNull @PhoneNumberAvailable String phoneNumber,
    @NotNull @EnumConstraint(PersonType.class) String personType
) {

    record PersonAddress(
        @NotEmpty String city,
        @NotEmpty String street
    ) {
    }
}
