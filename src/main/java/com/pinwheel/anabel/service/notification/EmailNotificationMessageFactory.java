package com.pinwheel.anabel.service.notification;

import org.springframework.stereotype.Service;

/**
 * Email notification message factory. Extends {@link NotificationMessageFactory}. Contains factory methods for getting
 * ready email notification messages.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Service
public class EmailNotificationMessageFactory extends NotificationMessageFactory {
    /**
     * Returns ready sign up {@link EmailNotificationMessage email notification message} object.
     *
     * @return email notification message for sign up.
     */
    public NotificationMessage createSignup() {
        return EmailNotificationMessage.builder()
                .message("Signup message")
                .build();
    }
}
