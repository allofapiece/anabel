package com.pinwheel.anabel.service.notification.notifier;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.service.notification.NotificationMailSender;
import com.pinwheel.anabel.service.notification.domain.EmailNotificationMessage;
import com.pinwheel.anabel.service.notification.domain.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Notifier which use email sender for sending email notifications.
 *
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class EmailNotifier implements Notifier {
    /**
     * Injection of {@link NotificationMailSender}.
     */
    private final NotificationMailSender mailSender;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean send(User user, NotificationMessage message) {
        EmailNotificationMessage emailMessage = (EmailNotificationMessage) message;
        emailMessage.setTo(user.getEmail());

        return send(emailMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Async
    public CompletableFuture<Boolean> sendAsync(User user, NotificationMessage message) {
        return CompletableFuture.completedFuture(this.send(user, message));
    }

    /**
     * Asynchronously sends mail to the target email.
     *
     * @param message email notification message.
     * @return whether message will be sent.
     */
    public boolean send(EmailNotificationMessage message) {
        return mailSender.send(message);
    }
}
