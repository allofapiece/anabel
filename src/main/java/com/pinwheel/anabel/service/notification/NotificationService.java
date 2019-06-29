package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.service.notification.domain.Notification;
import com.pinwheel.anabel.service.notification.domain.NotificationMessage;
import com.pinwheel.anabel.service.notification.notifier.Notifier;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Notification service. Contains general logic for processing notification messages.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public interface NotificationService {
    /**
     * For each notifier of the notifiers map invokes {@link NotificationService#send(String, Map, List)} method.
     * {@link Notification} instance should be passed.
     *
     * @return whether all notification message have been sent successfully.
     */
    boolean send(Notification notification);

    /**
     * Sends notifications for all users from map notification message. Notification sending will be processed by passed
     * {@link Notifier}.
     *
     * @param notifierName notifier name for resolving target notifier.
     * @param map          map where key is target user and value is notification message.
     * @param conditions   list of condition which will filter users.
     * @return whether all notification message have been sent successfully.
     */
    boolean send(String notifierName, Map<User, NotificationMessage> map, List<Predicate<User>> conditions);

    /**
     * For each notifier of the notifiers map invokes {@link NotificationService#sendAsync(String, Map, List)} method.
     * {@link Notification} instance should be passed.
     *
     * @return whether all notification message have been sent successfully.
     */
    void sendAsync(Notification notification);

    /**
     * Sends notifications asynchronously for all users from map notification message. Notification sending will be processed by passed
     * {@link Notifier}.
     *
     * @param notifierName notifier name for resolving target notifier.
     * @param map          map where key is target user and value is notification message.
     * @param conditions   list of condition which will filter users.
     * @return whether all notification message have been sent successfully.
     */
    void sendAsync(String notifierName, Map<User, NotificationMessage> map, List<Predicate<User>> conditions);
}
