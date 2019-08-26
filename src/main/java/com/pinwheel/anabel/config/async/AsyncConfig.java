package com.pinwheel.anabel.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * Async configuration. If profile `non-async` is enabled {@link NonAsyncConfig} will be used instead.
 *
 * @version 1.0.0
 */
@Configuration
@Profile("!non-async")
@EnableAsync(proxyTargetClass = true)
public class AsyncConfig extends AsyncConfigurerSupport {
    /**
     * Set up async executor and return this. Set up pool size to 10.
     *
     * @return {@link ContextAwarePoolExecutor} bean.
     */
    @Bean
    public Executor getAsyncExecutor() {
        ContextAwarePoolExecutor contextAwarePoolExecutor = new ContextAwarePoolExecutor();
        contextAwarePoolExecutor.setCorePoolSize(10);

        return contextAwarePoolExecutor;
    }
}
