package com.pinwheel.anabel.service.validation;

import lombok.RequiredArgsConstructor;
import org.passay.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Constraint validator for user password.
 *
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    /**
     * Inject of {@link ResourceLoader} bean.
     */
    private final ResourceLoader loader;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final ValidPassword arg0) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        if (password == null) {
            return true;
        }

        final PasswordValidator validator = new PasswordValidator(getMessageResolver(), Arrays.asList(
                new LengthRule(4, 18),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new WhitespaceRule())
        );

        final RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);

        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }

    /**
     * Returns message resolver.
     *
     * @return {@link MessageResolver} bean.
     */
    private MessageResolver getMessageResolver() {
        String localeName = LocaleContextHolder.getLocale().getLanguage();
        String fileName = "classpath:i18n/forms" +
                ("en".equals(localeName) ? "" : "_" + localeName) +
                ".properties";

        Resource resource = loader.getResource(fileName);
        Properties props = new Properties();

        try {
            FileInputStream inputStream = new FileInputStream(resource.getFile());
            props.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new PropertiesMessageResolver(props);
    }
}
