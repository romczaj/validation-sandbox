package pl.romczaj.validation.person;

public record RegisterAccountRequestProgrammatic(
    String firstName,
    String lastName,
    String email,
    RegisterAccountRequest.PersonAddress personAddress,
    String phoneNumber,
    String personType
) {

    record PersonAddress(
        String city,
        String street
    ) {
    }
}