package pl.romczaj.validation.person.phone;

import org.springframework.stereotype.Component;

@Component
public class PhoneNumberRegistry {

    public boolean isAvailable(String phoneNumber) {
        return !phoneNumber.contains("666");
    }
}
