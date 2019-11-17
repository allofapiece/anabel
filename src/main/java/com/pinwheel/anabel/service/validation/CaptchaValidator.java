package com.pinwheel.anabel.service.validation;

import com.pinwheel.anabel.entity.Captcha;
import com.pinwheel.anabel.service.CaptchaService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@RequiredArgsConstructor
public class CaptchaValidator implements ConstraintValidator<ValidCaptcha, Captcha> {
    private final CaptchaService captchaService;

    private String message;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final ValidCaptcha constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final Captcha captcha, final ConstraintValidatorContext context) {
        if (!captchaService.verify(captcha.getGRecaptchaResponse())) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("gRecaptchaResponse")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
