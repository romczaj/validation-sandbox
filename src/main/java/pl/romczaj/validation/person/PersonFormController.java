package pl.romczaj.validation.person;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonFormController {

    @PostMapping("/register-account-declarative")
    public void registerAccountDeclarative(@RequestBody @Valid RegisterAccountRequest registerAccountRequest) {
        log.info("User has been registered (declarative)");
    }

}
