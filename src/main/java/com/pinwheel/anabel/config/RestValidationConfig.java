package com.pinwheel.anabel.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@RequiredArgsConstructor
@Configuration
public class RestValidationConfig extends RepositoryRestConfigurerAdapter {
    private final LocalValidatorFactoryBean validator;

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }


    @Override
    public void configureJacksonObjectMapper(final ObjectMapper objectMapper) {
        objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}
