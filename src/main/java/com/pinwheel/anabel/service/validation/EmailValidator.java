package com.pinwheel.anabel.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email validator. Validates email by pattern and email already existing.
 *
 * @version 1.0
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    /**
     * Email pattern.
     */
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final ValidEmail constraintAnnotation) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        if (username == null) {
            return true;
        }

        return validateEmail(username);
    }

    /**
     * Validates email.
     *
     * @param email target email.
     * @return whether passed email is valid.
     */
    private boolean validateEmail(final String email) {
        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(email);

        return matcher.matches();
    }
}
