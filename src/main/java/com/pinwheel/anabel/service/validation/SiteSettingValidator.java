package com.pinwheel.anabel.service.validation;

import com.pinwheel.anabel.entity.SiteSettingType;
import com.pinwheel.anabel.entity.dto.SiteSettingDto;
import com.pinwheel.anabel.service.SiteSettingService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * Validator of {@link com.pinwheel.anabel.entity.SiteSetting} instance.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
public class SiteSettingValidator implements ConstraintValidator<ValidSiteSetting, SiteSettingDto> {
    /**
     * Inject of {@link SiteSettingService} bean.
     */
    private final SiteSettingService siteSettingService;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(SiteSettingDto value, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (SiteSettingType.BOOLEAN.equals(value.getType()) && !List.of("true", "false").contains(value.getValue())) {
            context.buildConstraintViolationWithTemplate("for boolean type value can has only `true` or `false` value")
                    .addPropertyNode("value")
                    .addConstraintViolation();

            isValid = false;
        }

        if (value.getId() == null && keyExists(value.getKey())) {
            context.buildConstraintViolationWithTemplate("this key already exists")
                    .addPropertyNode("key")
                    .addConstraintViolation();

            isValid = false;
        }

        return isValid;
    }

    /**
     * Check whether key exists in database for provided key.
     *
     * @param key key of the setting.
     * @return whether key exists in database.
     */
    private boolean keyExists(String key) {
        return siteSettingService.exists(key);
    }
}
