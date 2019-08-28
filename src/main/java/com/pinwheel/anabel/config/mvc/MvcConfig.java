package com.pinwheel.anabel.config.mvc;

import com.pinwheel.anabel.service.handler.AssetsMethodReturnValueHandler;
import com.pinwheel.anabel.service.interceptor.FreeMarkerInterceptor;
import com.pinwheel.anabel.service.interceptor.SecurityInterceptor;
import com.pinwheel.anabel.service.module.asset.AssetManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * Spring MVC configuration.
 *
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
    private final AssetManager assetManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("auth/login");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * Set up and return {@link ReloadableResourceBundleMessageSource} bean. Set up basename as
     * {@code classpath:i18n/messages}.
     *
     * @return implementation of {@link MessageSource}.
     */
    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:i18n/messages", "classpath:i18n/forms", "classpath:i18n/general");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    /**
     * Set up and returns {@link SessionLocaleResolver} bean. Set up default locale as {@code ENGLISH}.
     *
     * @return {@link LocaleResolver} bean.
     */
    @Bean
    public LocaleResolver localeResolver() {
        final SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);

        return sessionLocaleResolver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);

        final SecurityInterceptor securityInterceptor = new SecurityInterceptor();
        registry.addInterceptor(securityInterceptor);

        final FreeMarkerInterceptor freeMarkerInterceptor = new FreeMarkerInterceptor();
        registry.addInterceptor(freeMarkerInterceptor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new AssetsMethodReturnValueHandler(assetManager));
    }

    /**
     * Set up and returns {@link ModelMapper} bean.
     *
     * @return {@link ModelMapper} bean.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Set up and returns {@link RestTemplate} bean.
     *
     * @return {@link RestTemplate} bean.
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}