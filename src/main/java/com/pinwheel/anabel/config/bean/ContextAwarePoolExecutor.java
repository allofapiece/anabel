package com.pinwheel.anabel.config.bean;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Custom implementation of {@link ThreadPoolTaskExecutor} class. Sets request attributes and locale context to new
 * {@link Callable} or {@link Runnable} object.
 *
 * @version 1.0.0
 */
@SuppressWarnings("all")
public class ContextAwarePoolExecutor extends ThreadPoolTaskExecutor {
    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(new ContextAwareCallable(
                task,
                RequestContextHolder.currentRequestAttributes(),
                LocaleContextHolder.getLocaleContext())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return super.submitListenable(new ContextAwareCallable(
                task,
                RequestContextHolder.currentRequestAttributes(),
                LocaleContextHolder.getLocaleContext())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Runnable task) {
        super.execute(new ContextAwareRunnable(
                task,
                RequestContextHolder.currentRequestAttributes(),
                LocaleContextHolder.getLocaleContext())
        );
    }
}
