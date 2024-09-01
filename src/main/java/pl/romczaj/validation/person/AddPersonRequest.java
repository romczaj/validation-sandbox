package pl.romczaj.validation.person;

import jakarta.validation.constraints.NotEmpty;

record AddPersonRequest(
    @NotEmpty String firstName,
    @NotEmpty String lastName
) {
}
