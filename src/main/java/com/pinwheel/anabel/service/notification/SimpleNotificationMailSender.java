package com.pinwheel.anabel.service.notification;

import com.pinwheel.anabel.service.mail.MailSender;
import com.pinwheel.anabel.service.mail.SimpleMailSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

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
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);

    /**
     * {@inheritDoc}
     */
    public boolean send(EmailNotificationMessage message) {
        if (message.getMessage() != null) {
            message.getModel().put("message", message.getMessage());
        }

        boolean result = false;

        try {
            result = simpleMailSender.send(
                    message.getTo(),
                    message.getSubject(),
                    message.getTemplate(),
                    message.getModel(),
                    message.getMultiPartMode(),
                    message.getCharset()
            ).get();
        } catch (ExecutionException | InterruptedException e) {
            logger.error("An error occurred while sending email.", e);
        }

        return result;
    }
}
