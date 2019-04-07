package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;

import java.util.Collection;
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
     * For each notifier of the notifiers map invokes {@link NotificationService#send(Notifier, Map)} method.
     *
     * @return whether all notification message have been sent successfully.
     */
    boolean send();

    /**
     * Sends for all users from map notification message. Notification sending will be processed by passed
     * {@link Notifier}.
     *
     * @param notifier notifier which will be processing the message.
     * @param map      map where key is target user and value is notification message.
     * @return whether all notification message have been sent successfully.
     */
    boolean send(Notifier notifier, Map<User, NotificationMessage> map);

    /**
     * Puts {@link Notifier} and user to which notification message will be sent.
     *
     * @param notifier notifier, which will process the notification message.
     * @param user     target user.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService put(Notifier notifier, User user);

    /**
     * Puts {@link Notifier} and user to which notification message will be sent.
     *
     * @param notifierName notifier name for resolving notifier instance.
     * @param user         target user.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService put(String notifierName, User user);

    /**
     * Puts {@link Notifier}, user to which notification message will be sent and specific notification message for
     * target user.
     *
     * @param notifier notifier, which will process the notification message.
     * @param user     target user.
     * @param message  specific notification message.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService put(Notifier notifier, User user, NotificationMessage message);

    /**
     * Puts {@link Notifier}, user to which notification message will be sent and specific notification message for
     * target user.
     *
     * @param notifierName notifier name for resolving notifier instance.
     * @param user         target user.
     * @param message      specific notification message.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService put(String notifierName, User user, NotificationMessage message);

    /**
     * Puts {@link Notifier}, users to which notification message will be sent.
     *
     * @param notifier notifier, which will process the notification message.
     * @param users    target users.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService put(Notifier notifier, Collection<User> users);

    /**
     * Puts {@link Notifier}, users to which notification message will be sent.
     *
     * @param notifierName notifier name for resolving notifier instance.
     * @param users        target users.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService put(String notifierName, Collection<User> users);

    /**
     * Puts {@link Notifier}, users to which notification message will be sent and specific notification message for
     * target users.
     *
     * @param notifier notifier, which will process the notification message.
     * @param users    target users.
     * @param message  specific notification message.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService put(Notifier notifier, Collection<User> users, NotificationMessage message);

    /**
     * Puts {@link Notifier}, users to which notification message will be sent and specific notification message for
     * target users.
     *
     * @param notifierName notifier name for resolving notifier instance.
     * @param users        target users.
     * @param message      specific notification message.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService put(String notifierName, Collection<User> users, NotificationMessage message);

    /**
     * Sets default message for methods that do not accept a notification messages.
     *
     * @param message specific notification message.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService setMessage(NotificationMessage message);

    /**
     * Sets conditions for filtering target users.
     *
     * @param conditions conditions for filtering.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService setConditions(Collection<Predicate<User>> conditions);

    /**
     * Adds condition for filtering target users.
     *
     * @param condition condition for filtering.
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService addCondition(Predicate<User> condition);

    /**
     * Removes condition of filtering target users.
     *
     * @param condition condition to remove
     * @return this class instance following the rules of the builder pattern.
     */
    SimpleNotificationService removeCondition(Predicate<User> condition);
}
