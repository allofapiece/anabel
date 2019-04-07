package com.pinwheel.anabel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * Freemarker config. Contains configuration for freemarker engine.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Configuration
public class FreemarkerConfig {

    /**
     * Sets up free marker configuration factory bean. Sets template loader path.
     *
     * @return free marker configuration factory bean.
     */
    @Bean
    @Primary
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/mail/templates/");

        return bean;
    }
}
