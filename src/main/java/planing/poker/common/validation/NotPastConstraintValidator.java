package planing.poker.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NotPastConstraintValidator implements ConstraintValidator<NotPast, Object> {

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return true;
        }

        if (object instanceof LocalDate value) {
            return !value.isBefore(LocalDate.now());
        }  else if (object instanceof LocalDateTime value) {
            return !value.isBefore(LocalDateTime.now());
        }

        return false;
    }
}
