package com.pinwheel.anabel.config.mvc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Priority;
import java.util.ArrayList;

/**
 * Bean post processor for sorting return value handlers according on priority. For specify priority can be use
 * {@link Priority} annotation. By default handlers have 0 priority.
 *
 * @version 1.0.0
 */
@Configuration
public class ReturnValueHandlerPriorityBeanPostProcessor implements BeanPostProcessor {
    /**
     * {@inheritDoc}
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RequestMappingHandlerAdapter) {
            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
            var returnValueHandlers = adapter.getReturnValueHandlers();

            if (returnValueHandlers != null) {
                var newList = new ArrayList<>(returnValueHandlers);
                newList.sort((l, r) -> priority(r) - priority(l));
                adapter.setReturnValueHandlers(newList);
            }
        }

        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * Retrieves priority of the passed return value handler. If handler does not have {@link Priority} annotation,
     * 0 will be returned.
     *
     * @param handler return value handler for retrieving priority.
     * @return priority of return value handler.
     */
    protected int priority(HandlerMethodReturnValueHandler handler) {
        Priority priority = AnnotationUtils.findAnnotation(handler.getClass(), Priority.class);
        return priority != null ? priority.value() : 0;
    }
}
