package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.service.mail.SimpleMailSender;
import com.pinwheel.anabel.service.notification.domain.Notification;
import com.pinwheel.anabel.service.notification.domain.NotificationMessage;
import com.pinwheel.anabel.service.notification.notifier.Notifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
public class SimpleNotificationService implements NotificationService {
    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);

    /**
     * Injection of {@link NotifierResolver} bean.
     */
    private final NotifierResolver notifierResolver;

    /**
     * {@inheritDoc}
     */
    public boolean send(Notification notification) {
        Map<String, Map<User, NotificationMessage>> map = notification.getMap();

        return map.size() == map.entrySet()
                .stream()
                .filter(notifier -> send(notifier.getKey(), notifier.getValue(), notification.getConditions()))
                .count();
    }

    /**
     * {@inheritDoc}
     */
    public boolean send(String notifierName, Map<User, NotificationMessage> map, List<Predicate<User>> conditions) {
        Notifier notifier = notifierResolver.resolve(notifierName);

        return toStream(map, conditions)
                .allMatch((entry) -> notifier.send(entry.getKey(), entry.getValue()));
    }

    @Override
    public void sendAsync(Notification notification) {
        notification.getMap().forEach((key, value) -> sendAsync(key, value, notification.getConditions()));
    }

    /**
     * {@inheritDoc}
     */
    public void sendAsync(String notifierName, Map<User, NotificationMessage> map, List<Predicate<User>> conditions) {
        Notifier notifier = notifierResolver.resolve(notifierName);
        toStream(map, conditions).forEach((entry) -> notifier
                .sendAsync(entry.getKey(), entry.getValue())
                .exceptionally(e -> {
                    logger.error("Message has not been sent.", e);
                    return null;
                })
        );
    }

    /**
     * Makes stream from map of notification messages filtered by {@code conditions}.
     *
     * @param map        map of users and theirs notifications.
     * @param conditions conditions for filtering messages.
     * @return result stream.
     */
    protected Stream<Map.Entry<User, NotificationMessage>> toStream(
            Map<User, NotificationMessage> map,
            List<Predicate<User>> conditions
    ) {
        Stream<Map.Entry<User, NotificationMessage>> stream = map
                .entrySet()
                .stream();

        for (Predicate<User> condition : conditions) {
            stream = stream.filter(entry -> condition.test(entry.getKey()));
        }

        return stream;
    }
}
