package com.pinwheel.anabel.config;

import com.pinwheel.anabel.service.notification.EmailNotifier;
import com.pinwheel.anabel.service.notification.FlushNotifier;
import com.pinwheel.anabel.service.notification.NotifiersHolder;
import com.pinwheel.anabel.service.notification.WebNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Notifications config.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Configuration
public class NotificationConfig {
    /**
     * Injection of {@link EmailNotifier} bean.
     */
    private final EmailNotifier emailNotifier;

    /**
     * Injection of {@link WebNotifier} bean.
     */
    private final WebNotifier webNotifier;

    /**
     * Injection of {@link FlushNotifier} bean.
     */
    private final FlushNotifier flushNotifier;

    /**
     * Set up notifiers holder bean. Adds email, web and flush notifiers to holder.
     *
     * @return notifiers holder.
     */
    @Bean
    public NotifiersHolder notifiersHolder() {
        return NotifiersHolder.builder()
                .notifier("email", this.emailNotifier)
                .notifier("web", this.webNotifier)
                .notifier("flush", this.flushNotifier)
                .build();
    }
}
