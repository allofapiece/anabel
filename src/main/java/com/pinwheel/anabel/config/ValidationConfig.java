package com.pinwheel.anabel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Validation config.
 *
 * @version 1.0.0
 */
@Configuration
public class ValidationConfig {
    /**
     * Returns {@link LocalValidatorFactoryBean} bean instance.
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}
