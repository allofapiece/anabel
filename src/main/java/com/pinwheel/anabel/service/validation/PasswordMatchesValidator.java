package com.pinwheel.anabel.service.validation;

import com.pinwheel.anabel.entity.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for matching main and confirmed passwords.
 *
 * @version 1.0
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDto user = (UserDto) obj;
        return user.getPassword().equals(user.getConfirmedPassword());
    }
}
