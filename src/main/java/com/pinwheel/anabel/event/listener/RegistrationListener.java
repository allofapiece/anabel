package com.pinwheel.anabel.event.listener;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.event.OnRegistrationCompleteEvent;
import com.pinwheel.anabel.service.VerificationTokenService;
import com.pinwheel.anabel.service.notification.NotificationService;
import com.pinwheel.anabel.service.notification.domain.Notification;
import com.pinwheel.anabel.service.notification.factory.EmailNotificationMessageFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Registration listener. Implementation of {@link ApplicationListener} class.
 *
 * @version 1.0
 */
@AllArgsConstructor
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    /**
     * Injection of {@link VerificationTokenService} bean.
     */
    private final VerificationTokenService verificationTokenService;

    /**
     * Injection of {@link NotificationService} bean.
     */
    private final NotificationService notificationService;

    /**
     * Injection of {@link EmailNotificationMessageFactory} bean.
     */
    private final EmailNotificationMessageFactory factory;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    /**
     * After registration logic.
     *
     * @param event event of registration completing.
     */
    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();

        verificationTokenService.create(user);

        notificationService.sendAsync(Notification.builder()
                .put("email", user, factory.create("signup", user))
                .build());
    }
}
