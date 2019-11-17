package com.pinwheel.anabel.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;


@Documented
@Constraint(validatedBy = UniqueSlugValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface UniqueSlug {
    String message() default "{form.user-slug.slug.error.taken}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
