package planing.poker.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

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
    public boolean isValid(final String string, final ConstraintValidatorContext constraintValidatorContext) {
        return validatePassword(string);
    }

    private boolean validatePassword(final String password) {
        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }

        int upperCaseChars = 0;
        int lowerCaseChars = 0;
        int specialChars = 0;

        for (Character c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCaseChars++;
            } else if (Character.isLowerCase(c)) {
                lowerCaseChars++;
            } else if (!Character.isLetterOrDigit(c)) {
                specialChars++;
            }
        }

        return upperCaseChars >= upperCaseCount && lowerCaseChars >= lowerCaseCount && specialChars >= specialCharCount;
    }

}
