package pl.romczaj.validation.person.email;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EmailRegistry {

    private final List<String> emailDatabase;

    EmailRegistry() {
        emailDatabase = new ArrayList<>();
        emailDatabase.add("jan.kowalski@email.com");
    }

    public boolean isAlreadyUsed(String email) {
        return emailDatabase.contains(email);
    }
}
