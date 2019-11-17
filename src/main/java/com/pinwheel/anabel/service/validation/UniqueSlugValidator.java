package com.pinwheel.anabel.service.validation;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.repository.UserRepository;
import com.pinwheel.anabel.service.SiteSettingService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


@RequiredArgsConstructor
public class UniqueSlugValidator implements ConstraintValidator<ValidSlug, Object> {
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
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        User user = (User) value;
        String slug = user.getSlug();

        if (slug == null) {
            return true;
        }

        return !(slugExists(slug, user.getId()) || ((List<String>) siteSettingService.getValue("slugTakenKeywords")).contains(slug));
    }

    /**
     *
     */
    private boolean slugExists(String slug, Long id) {
        return id == null ? userRepository.existsBySlug(slug) : userRepository.existsBySlugAndIdNot(slug, id);
    }
}
