package pl.romczaj.validation.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.romczaj.validation.person.RegisterAccountValidator;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
class AppMvcConfiguration {

//    Does not work
//    @InitBinder
//    public void initBinder(WebDataBinder binder, PersonValidator personValidator) {
//        binder.addValidators(personValidator);
//    }

//    @Bean
//    public RegisterAccountValidator personValidator() {
//        return new RegisterAccountValidator();
//    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);


        return mapper;
    }
}

