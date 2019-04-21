package com.pinwheel.anabel.config.bean;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;

/**
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public class ContextAwareCallable<T> implements Callable<T> {
    private Callable<T> task;
    private RequestAttributes context;
    private LocaleContext localeContext;

    public ContextAwareCallable(Callable<T> task, RequestAttributes context, LocaleContext localeContext) {
        this.task = task;
        this.context = context;
        this.localeContext = localeContext;
    }

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
