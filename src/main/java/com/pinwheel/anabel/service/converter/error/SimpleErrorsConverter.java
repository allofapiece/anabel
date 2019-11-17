package com.pinwheel.anabel.service.converter.error;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class SimpleErrorsConverter implements ErrorsConverter {
    @Override
    public Object convert(Set<ConstraintViolation<?>> constraintViolations) {
        return convert(constraintViolations.stream());
    }

    @Override
    public Object convert(BindingResult bindingResult) {
        return convert(bindingResult.getAllErrors()
                .stream()
                .map(error -> error.unwrap(ConstraintViolationImpl.class)));
    }

    @Override
    public Object convert(Errors errors) {
        return convert(errors.getAllErrors()
                .stream()
                .map(error -> error.unwrap(ConstraintViolationImpl.class)));
    }

    protected Object convert(Stream<ConstraintViolation<?>> stream) {
        return stream.map(this::mapErrors)
                .collect(Collectors.toSet());
    }

    protected Map mapErrors(ConstraintViolation<?> constraintViolation) {
        var map = new HashMap<>();

        map.put("property", getProperty(constraintViolation));
        map.put("message", getMessage(constraintViolation));
        map.put("value", getValue(constraintViolation));
        map.put("type", getType(constraintViolation));

        return map;
    }

    protected Object getProperty(ConstraintViolation<?> constraintViolation) {
        return getType(constraintViolation).equals(ElementType.FIELD)
                ? constraintViolation.getPropertyPath().toString()
                : null;
    }

    protected Object getMessage(ConstraintViolation<?> constraintViolation) {
        return constraintViolation.getMessage();
    }

    protected Object getValue(ConstraintViolation<?> constraintViolation) {
        return getType(constraintViolation).equals(ElementType.FIELD)
                ? constraintViolation.getInvalidValue()
                : null;
    }

    protected ElementType getType(ConstraintViolation<?> constraintViolation) {
        return ((ConstraintDescriptorImpl) ((ConstraintViolationImpl) constraintViolation)
                .getConstraintDescriptor()).getElementType();
    }
}
