package com.pinwheel.anabel.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Annotation for {@link com.pinwheel.anabel.entity.SiteSetting} instance validation.
 *
 * @version 1.0.0
 */
@Documented
@Constraint(validatedBy = SiteSettingValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, ANNOTATION_TYPE})
public @interface ValidSiteSetting {
    String message() default "value is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
