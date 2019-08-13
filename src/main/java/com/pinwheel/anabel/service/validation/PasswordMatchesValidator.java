package com.pinwheel.anabel.service.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Validator for matching main and confirmed passwords.
 *
 * @version 1.0
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    private String passwordPropertyName;

    private String confirmedPasswordPropertyName;

    private String affectedObject;

    private String message;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        passwordPropertyName = constraintAnnotation.passwordPropertyName();
        confirmedPasswordPropertyName = constraintAnnotation.confirmedPasswordPropertyName();
        affectedObject = constraintAnnotation.affectedObject();
        message = constraintAnnotation.message();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(affectedObject)) {
            return getPasswordValue(obj, passwordPropertyName).equals(getPasswordValue(obj, confirmedPasswordPropertyName));
        }

        if (!List.of(passwordPropertyName, confirmedPasswordPropertyName).contains(affectedObject)) {
            findProperty(obj, affectedObject);
        }

        if (!getPasswordValue(obj, passwordPropertyName).equals(getPasswordValue(obj, confirmedPasswordPropertyName))) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(affectedObject)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }

    private String getPasswordValue(Object obj, String propertyName) {
        try {
            return (String) findProperty(obj, propertyName).getReadMethod().invoke(obj);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("An error occurred while validate passwords.");
        }
    }

    private PropertyDescriptor findProperty(Object obj, String property) {
        try {
            Optional<PropertyDescriptor> propertyDescriptor = Arrays.stream(Introspector.getBeanInfo(obj.getClass())
                    .getPropertyDescriptors())
                    .filter(p -> p.getName().equals(property))
                    .findFirst();

            if (propertyDescriptor.isEmpty()) {
                throw new RuntimeException("Object of validation does not has property " + property + ".");
            }

            return propertyDescriptor.get();
        } catch (IntrospectionException e) {
            throw new RuntimeException("An error occurred while validate passwords.");
        }
    }
}
