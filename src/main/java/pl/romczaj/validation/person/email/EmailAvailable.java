package pl.romczaj.validation.person.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailAvailableValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailAvailable {
    String message() default "Email is already used";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
