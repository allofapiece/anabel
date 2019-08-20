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
public class ContextAwareRunnable implements Runnable {
    /**
     * Custom implementation of {@link Callable} class. Sets request attributes and locale context to new {@link Callable}
     * object.
     */
    private Runnable task;

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
    public void run() {
        if (context != null) {
            RequestContextHolder.setRequestAttributes(context);
            LocaleContextHolder.setLocaleContext(localeContext);
        }

        try {
            task.run();
        } finally {
            RequestContextHolder.resetRequestAttributes();
            LocaleContextHolder.resetLocaleContext();
        }
    }
}
