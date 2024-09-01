package pl.romczaj.validation.person;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PersonFormController {

    @PostMapping("/add-person")
    public void addPerson(@RequestBody @Valid AddPersonRequest addPersonRequest) {

    }
}
