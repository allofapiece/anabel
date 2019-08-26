package com.pinwheel.anabel.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "{form.user.password.error.match}";

    String passwordPropertyName() default "password";

    String confirmedPasswordPropertyName() default "confirmedPassword";

    String affectedObject() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
