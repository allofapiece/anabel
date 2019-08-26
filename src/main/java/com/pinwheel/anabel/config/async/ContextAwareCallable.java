package com.pinwheel.anabel.config.async;

import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;

/**
 * Custom implementation of {@link Callable} class. Sets request attributes and locale context to new {@link Callable}
 * object.
 *
 * @version 1.0.0
 */
@AllArgsConstructor
public class ContextAwareCallable<T> implements Callable<T> {
    /**
     * Default {@link Callable} for calling.
     */
    private Callable<T> task;

    /**
     * {@link RequestAttributes} object for passing it in new callable task.
     */
    private RequestAttributes context;

    /**
     * {@link LocaleContext} object for passing it in new callable task.
     */
    private LocaleContext localeContext;

    /**
     * {@inheritDoc}
     */
    @Override
    public T call() throws Exception {
        if (context != null) {
            RequestContextHolder.setRequestAttributes(context);
            LocaleContextHolder.setLocaleContext(localeContext);
        }

        try {
            return task.call();
        } finally {
            RequestContextHolder.resetRequestAttributes();
            LocaleContextHolder.resetLocaleContext();
        }
    }
}
