package com.pinwheel.anabel.service.notification.notifier;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.service.notification.domain.NotificationMessage;

import java.util.concurrent.CompletableFuture;

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
     * @param user    target user.
     * @param message notification message.
     * @return whether notification message has been sent.
     */
    boolean send(User user, NotificationMessage message);

    /**
     * Sends notification message asynchronously.
     *
     * @param user    target user.
     * @param message notification message.
     * @return whether notification message has been sent.
     */
    CompletableFuture<Boolean> sendAsync(User user, NotificationMessage message);
}
