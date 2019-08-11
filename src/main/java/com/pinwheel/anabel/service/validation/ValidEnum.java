package com.pinwheel.anabel.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * Annotation for enum validation.
 *
 * @version 1.0.0
 */
@Documented
@Constraint(validatedBy = EnumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface ValidEnum {
    /**
     * Enum class that will be validated.
     *
     * @return enum class.
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * Enum values which can be used for value of entity.
     *
     * @return allowed enum values.
     */
    String[] allowed();

    String message() default "Value is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
