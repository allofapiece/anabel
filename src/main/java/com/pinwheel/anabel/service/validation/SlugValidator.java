package com.pinwheel.anabel.service.validation;

import com.pinwheel.anabel.repository.UserRepository;
import com.pinwheel.anabel.service.SiteSettingService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


@RequiredArgsConstructor
public class SlugValidator implements ConstraintValidator<ValidSlug, String> {
    /**
     * Inject of {@link UserRepository} bean.
     */
    private final UserRepository userRepository;

    /**
     * Inject of {@link SiteSettingService} bean.
     */
    private final SiteSettingService siteSettingService;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !(slugExists(value) || ((List<String>) siteSettingService.getValue("slugTakenKeywords")).contains(value));
    }

    private boolean slugExists(String slug) {
        return userRepository.existsBySlug(slug);
    }
}
