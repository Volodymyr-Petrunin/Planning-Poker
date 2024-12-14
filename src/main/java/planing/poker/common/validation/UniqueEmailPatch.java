package planing.poker.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueEmailPatchValidator.class)
@Documented
public @interface UniqueEmailPatch {

    String message() default "{messages.validation.email.in.use}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

