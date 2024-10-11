package planing.poker.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {PasswordConstraintValidator.class})
@Documented
public @interface Password {
    String message() default "{messages.validation.password}";

    int maxLength() default 100;

    int minLength() default 8;

    int upperCaseCount() default 2;

    int lowerCaseCount() default 2;

    int specialCharCount() default 1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
