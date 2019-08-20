package com.pinwheel.anabel.config.mvc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @version 1.0.0
 */
@Configuration
public class PropertyEditorRegistrarBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        if (bean instanceof RequestMappingHandlerAdapter) {
            WebBindingInitializer wbi = ((RequestMappingHandlerAdapter) bean).getWebBindingInitializer();
            if (wbi == null) {
                wbi = new ConfigurableWebBindingInitializer();
                ((RequestMappingHandlerAdapter) bean).setWebBindingInitializer(wbi);
            }

            if (wbi instanceof ConfigurableWebBindingInitializer) {
                ((ConfigurableWebBindingInitializer) wbi).setPropertyEditorRegistrar(new ExtendedPropertyEditorRegistrar());
            }
        }

        return bean;
    }
}
