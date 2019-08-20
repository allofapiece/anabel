package com.pinwheel.anabel.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

import java.util.concurrent.Executor;

/**
 * Async configuration. If profile `non-async` is disabled {@link AsyncConfig} will be used instead.
 *
 * @version 1.0.0
 */
@Configuration
@Profile("non-async")
public class NonAsyncConfig extends AsyncConfigurerSupport {
    /**
     * Set up async executor and return this. Set up pool size to 10.
     *
     * @return {@link ContextAwarePoolExecutor} bean.
     */
    @Bean
    public Executor getAsyncExecutor() {
        ContextAwarePoolExecutor contextAwarePoolExecutor = new ContextAwarePoolExecutor();
        contextAwarePoolExecutor.setCorePoolSize(1);

        return contextAwarePoolExecutor;
    }
}
