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
public class ContextAwareRunnable implements Runnable {
    private Runnable task;
    private RequestAttributes context;
    private LocaleContext localeContext;

    public ContextAwareRunnable(Runnable task, RequestAttributes context, LocaleContext localeContext) {
        this.task = task;
        this.context = context;
        this.localeContext = localeContext;
    }

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
