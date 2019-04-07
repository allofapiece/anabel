package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Service
public class WebNotifier implements Notifier {

    @Override
    public boolean send(User user, NotificationMessage message) {
        // TODO implement web notifier
        return true;
    }
}
