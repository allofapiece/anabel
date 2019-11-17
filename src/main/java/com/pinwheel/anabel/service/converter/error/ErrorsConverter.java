package com.pinwheel.anabel.service.converter.error;

import org.springframework.data.rest.core.ValidationErrors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @version 1.0.0
 */
public interface ErrorsConverter {
    Object convert(Set<ConstraintViolation<?>> constraintViolations);

    Object convert(BindingResult bindingResult);

    Object convert(Errors errors);
}
