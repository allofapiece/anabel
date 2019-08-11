package com.pinwheel.anabel.service.validation;

import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validator for enums. Checks whether value exists in allowed value list of {@link ValidEnum} annotation.
 *
 * @version 1.0.0
 */
@NoArgsConstructor
public class EnumValidator implements ConstraintValidator<ValidEnum, Enum> {
    /**
     * Allowed values of enum.
     */
    private List<String> valueList;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        if (!valueList.contains(value.toString())) {
            context.buildConstraintViolationWithTemplate("Value is not valid.")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();

        Enum[] values = enumClass.getEnumConstants();
        List<String> allowed = Arrays.asList(constraintAnnotation.allowed());

        valueList = Arrays.stream(values)
                .map(Enum::toString)
                .filter(allowed::contains)
                .collect(Collectors.toList());
    }
}
