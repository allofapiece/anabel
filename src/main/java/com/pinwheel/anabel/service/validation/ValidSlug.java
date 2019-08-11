package com.pinwheel.anabel.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * Annotation for {@link com.pinwheel.anabel.entity.SiteSetting} instance validation.
 *
 * @version 1.0.0
 */
@Documented
@Constraint(validatedBy = SlugValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD})
public @interface ValidSlug {
    String message() default "{form.user-slug.slug.error.taken}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
