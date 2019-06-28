package com.pinwheel.anabel.service.notification.notifier;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.service.notification.domain.NotificationMessage;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Service
public class WebNotifier implements Notifier {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean send(User user, NotificationMessage message) {
        // TODO implement web notifier
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Boolean> sendAsync(User user, NotificationMessage message) {
        // TODO implement web notifier
        return CompletableFuture.completedFuture(true);
    }
}
