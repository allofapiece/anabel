package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.service.mail.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Simple notification mail sender. Implementation of {@link NotificationMailSender} interface.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SimpleNotificationMailSender implements NotificationMailSender {
    /**
     * Injection of {@link MailSender} bean.
     */
    private final MailSender simpleMailSender;

    /**
     * {@inheritDoc}
     */
    public boolean send(EmailNotificationMessage message) {
        return simpleMailSender.send(
                message.getTo(),
                message.getSubject(),
                message.getTemplate(),
                message.getModel(),
                message.getMultiPartMode(),
                message.getCharset()
        );
    }
}
