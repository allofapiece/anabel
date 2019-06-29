package com.pinwheel.anabel.service.notification.domain;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.service.notification.NotificationService;
import com.pinwheel.anabel.service.notification.notifier.Notifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.*;
import java.util.function.Predicate;

/**
 * Notification entity. Represents builder pattern for building notification. Notification should be passed to
 * {@link NotificationService} for sending.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    /**
     * Map for sending notification messages. Key of the map is target notifier which will process notification
     * messages. Value is another map that contains target users as keys and specific
     * {@link NotificationMessage notification message}.
     */
    private @Singular
    Map<String, Map<User, NotificationMessage>> map;

    /**
     * Conditions for filtering users before sending notifications.
     */
    private @Singular
    List<Predicate<User>> conditions;

    /**
     * Creates and returns notification builder.
     *
     * @return notification builder.
     */
    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

    /**
     * Notification Builder nested static class. Represents builder pattern.
     */
    public static class NotificationBuilder {
        Map<String, Map<User, NotificationMessage>> map = new HashMap<>();

        List<Predicate<User>> conditions = new LinkedList<>();

        /**
         * Builds {@link Notification} new instance according on notification builder data.
         *
         * @return new notification ready-state instance.
         */
        public Notification build() {
            return new Notification(this.map, this.conditions);
        }

        /**
         * Puts {@link Notifier}, user to which notification message will be sent and specific notification message for
         * target user.
         *
         * @param notifierName notifier name for resolving notifier instance.
         * @param user         target user.
         * @param message      specific notification message.
         * @return this class instance following the rules of the builder pattern.
         */
        public NotificationBuilder put(String notifierName, User user, NotificationMessage message) {
            if (!map.containsKey(notifierName)) {
                map.put(notifierName, new HashMap<>());
            }

            map.get(notifierName).put(user, message);

            return this;
        }

        /**
         * Puts {@link Notifier}, users to which notification message will be sent and specific notification message for
         * target users.
         *
         * @param notifierName notifier name for resolving notifier instance.
         * @param users        target users.
         * @param message      specific notification message.
         * @return this class instance following the rules of the builder pattern.
         */
        public NotificationBuilder put(String notifierName, Collection<User> users, NotificationMessage message) {
            for (User user : users) {
                this.put(notifierName, user, message);
            }

            return this;
        }

        /**
         * Sets conditions for filtering target users.
         *
         * @param conditions conditions for filtering.
         * @return this class instance following the rules of the builder pattern.
         */
        public NotificationBuilder conditions(Collection<Predicate<User>> conditions) {
            this.conditions = new ArrayList<>(conditions);
            return this;
        }

        /**
         * Adds condition for filtering target users.
         *
         * @param condition condition for filtering.
         * @return this class instance following the rules of the builder pattern.
         */
        public NotificationBuilder condition(Predicate<User> condition) {
            this.conditions.add(condition);
            return this;
        }

        /**
         * Removes condition of filtering target users.
         *
         * @param condition condition to remove
         * @return this class instance following the rules of the builder pattern.
         */
        public NotificationBuilder removeCondition(Predicate<User> condition) {
            this.conditions.remove(condition);
            return this;
        }
    }
}
