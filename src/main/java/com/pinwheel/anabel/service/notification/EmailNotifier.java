package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Notifier which use email sender for sending email notifications.
 *
 * @author Listratenko Stanislav
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
     * Asynchronously sends mail to the target email.
     *
     * @param message email notification message.
     * @return whether message will be sent.
     */
    public boolean send(EmailNotificationMessage message) {
        return mailSender.send(message);
    }
}
