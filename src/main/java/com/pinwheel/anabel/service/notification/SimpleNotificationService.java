package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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

        Stream<Map.Entry<User, NotificationMessage>> stream = map
                .entrySet()
                .stream();

        for (Predicate<User> condition : conditions) {
            stream = stream.filter(entry -> condition.test(entry.getKey()));
        }

        return stream.allMatch((entry) -> notifier.send(entry.getKey(), entry.getValue()));
    }
}
