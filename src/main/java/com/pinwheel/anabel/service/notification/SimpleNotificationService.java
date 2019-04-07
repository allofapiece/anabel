package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Simple notification service. Implementation of {@link NotificationService} interface.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
@Setter
@Service
@Scope("session")
public class SimpleNotificationService implements NotificationService {
    /**
     * Injection of {@link NotifierResolver} bean.
     */
    private final NotifierResolver notifierResolver;

    /**
     * Default notification message. It will be user for method which do not accept {@link NotificationMessage}.
     */
    private NotificationMessage defaultMessage;

    /**
     * Map for sending notification messages. Key of the map is target notifier which will process notification
     * messages. Value is another map that contains target users as keys and specific
     * {@link NotificationMessage notification message}.
     */
    private @Singular
    Map<Notifier, Map<User, NotificationMessage>> map = new HashMap<>();

    /**
     * Conditions for filtering users before sending notifications.
     */
    private @Singular
    List<Predicate<User>> conditions = new LinkedList<>();

    /**
     * {@inheritDoc}
     */
    public boolean send() {
        return map.size() == map.entrySet()
                .stream()
                .filter(notifier -> send(notifier.getKey(), notifier.getValue()))
                .count();
    }

    /**
     * {@inheritDoc}
     */
    public boolean send(Notifier notifier, Map<User, NotificationMessage> map) {
        Stream<Map.Entry<User, NotificationMessage>> stream = map
                .entrySet()
                .stream();

        for (Predicate<User> condition : conditions) {
            stream = stream.filter(entry -> condition.test(entry.getKey()));
        }

        return stream.allMatch((entry) -> notifier.send(entry.getKey(), entry.getValue()));
    }

    /**
     * {@inheritDoc}
     */
    public SimpleNotificationService put(Notifier notifier, User user, NotificationMessage message) {
        if (!map.containsKey(notifier)) {
            map.put(notifier, new HashMap<>());
        }

        map.get(notifier).put(user, message);

        return this;
    }

    /**
     * {@inheritDoc}
     */
    public SimpleNotificationService put(String notifierName, User user, NotificationMessage message) {
        this.put(this.notifierResolver.resolve(notifierName), user, message);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalNotificationStateException if default message does not set.
     */
    public SimpleNotificationService put(Notifier notifier, User user) {
        if (this.defaultMessage == null) {
            throw new IllegalNotificationStateException("Default message is not defined");
        }

        this.put(notifier, user, this.defaultMessage);

        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Uses {@link NotifierResolver notifier resolver} for resolving notifier by notifier name.
     */
    public SimpleNotificationService put(String notifierName, User user) {
        this.put(this.notifierResolver.resolve(notifierName), user);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public SimpleNotificationService put(Notifier notifier, Collection<User> users, NotificationMessage message) {
        for (User user : users) {
            this.put(notifier, user, message);
        }

        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Uses {@link NotifierResolver notifier resolver} for resolving notifier by notifier name.
     */
    public SimpleNotificationService put(String notifierName, Collection<User> users, NotificationMessage message) {
        this.put(this.notifierResolver.resolve(notifierName), users, message);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public SimpleNotificationService put(Notifier notifier, Collection<User> users) {
        for (User user : users) {
            this.put(notifier, user);
        }

        return this;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Uses {@link NotifierResolver notifier resolver} for resolving notifier by notifier name.
     */
    public SimpleNotificationService put(String notifierName, Collection<User> users) {
        this.put(this.notifierResolver.resolve(notifierName), users);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public SimpleNotificationService setMessage(NotificationMessage message) {
        this.defaultMessage = message;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public SimpleNotificationService setConditions(Collection<Predicate<User>> conditions) {
        this.conditions = new ArrayList<>(conditions);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public SimpleNotificationService addCondition(Predicate<User> condition) {
        this.conditions.add(condition);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public SimpleNotificationService removeCondition(Predicate<User> condition) {
        this.conditions.remove(condition);
        return this;
    }
}
