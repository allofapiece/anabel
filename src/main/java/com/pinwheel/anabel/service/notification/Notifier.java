package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;

/**
 * Notifier interface. Describes behavior of sending notifications.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public interface Notifier {
    /**
     * Sends notification message to the target user.
     *
     * @param user target user.
     * @param message notification message.
     * @return whether notification message has been sent.
     */
    boolean send(User user, NotificationMessage message);
}
