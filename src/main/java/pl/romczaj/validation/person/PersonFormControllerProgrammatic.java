package pl.romczaj.validation.person;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonFormControllerProgrammatic {

    private final RegisterAccountValidator registerAccountValidator;

    @PostMapping("/register-account-programmatic")
    public void registerAccountProgrammatic(@RequestBody @Valid RegisterAccountRequestProgrammatic registerAccountRequest) {
        log.info("User has been registered (programmatic)");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        log.info("initBinder invoked");
        binder.addValidators(registerAccountValidator);
    }


}
