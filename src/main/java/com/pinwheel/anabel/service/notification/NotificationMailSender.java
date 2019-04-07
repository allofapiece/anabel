package com.pinwheel.anabel.service.notification;

/**
 * Notification mail sender interface. Describes behavior of sending {@link EmailNotificationMessage email notification
 * messages}.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public interface NotificationMailSender {
    /**
     * Sends email notification message.
     *
     * @param message email notification message.
     * @return whether message has been sent.
     */
    boolean send(EmailNotificationMessage message);
}
