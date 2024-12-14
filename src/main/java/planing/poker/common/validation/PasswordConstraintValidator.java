package planing.poker.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PasswordConstraintValidator implements ConstraintValidator<Password, Object> {

    private int minLength;

    private int maxLength;

    private int upperCaseCount;

    private int lowerCaseCount;

    private int specialCharCount;

    @Override
    public void initialize(final Password password) {
        minLength = password.minLength();
        maxLength = password.maxLength();
        upperCaseCount = password.upperCaseCount();
        lowerCaseCount = password.lowerCaseCount();
        specialCharCount = password.specialCharCount();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        final String password;

        if (value instanceof String string) {
            password = string;
        } else if (value instanceof char[] chars) {
            password = new String(chars);
        } else {
            return false; // not supported type
        }

        return validatePassword(password);
    }

    private boolean validatePassword(final String password) {
        final char[] passwordChars = password.toCharArray();

        try {
            if (passwordChars.length < minLength || passwordChars.length > maxLength) {
                return false;
            }

            int upperCaseChars = 0;
            int lowerCaseChars = 0;
            int specialChars = 0;

            for (final char currentChar : passwordChars) {
                if (Character.isUpperCase(currentChar)) {
                    upperCaseChars++;
                } else if (Character.isLowerCase(currentChar)) {
                    lowerCaseChars++;
                } else if (!Character.isLetterOrDigit(currentChar)) {
                    specialChars++;
                }
            }

            return upperCaseChars >= upperCaseCount
                    && lowerCaseChars >= lowerCaseCount
                    && specialChars >= specialCharCount;
        } finally {
            Arrays.fill(passwordChars, '\0');
        }
    }

}
