package pl.romczaj.validation.person;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
class PersonFormController {

    private final RegisterAccountValidator registerAccountValidator;

    @PostMapping("/register-account-declarative")
    public void registerAccountDeclarative(@RequestBody @Valid RegisterAccountRequest registerAccountRequest) {
        log.info("User has been registered (declarative)");
    }

    @PostMapping("/register-account-programmatic")
    public void registerAccountProgrammatic(@RequestBody RegisterAccountRequest registerAccountRequest) {
        log.info("User has been registered (programmatic)");
        Errors errors = new BeanPropertyBindingResult(registerAccountRequest, "registerAccount");
        registerAccountValidator.validate(registerAccountRequest, errors);
    }

}
