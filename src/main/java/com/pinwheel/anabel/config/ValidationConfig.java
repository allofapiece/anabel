package com.pinwheel.anabel.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Validation config.
 *
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class ValidationConfig {
    /**
     * Returns {@link LocalValidatorFactoryBean} bean instance.
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setMessageInterpolator(
                new ResourceBundleMessageInterpolator(
                        new PlatformResourceBundleLocator("i18n/forms")));

        return bean;
    }
}
