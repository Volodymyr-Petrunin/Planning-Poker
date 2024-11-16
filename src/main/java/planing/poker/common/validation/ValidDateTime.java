package planing.poker.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidDateTimeValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface ValidDateTime {

    String message() default "{messages.validation.not.past}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
