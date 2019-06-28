package com.pinwheel.anabel.service.notification.factory;

import com.pinwheel.anabel.entity.User;
import com.pinwheel.anabel.service.notification.domain.EmailNotificationMessage;
import com.pinwheel.anabel.service.notification.domain.NotificationMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Email notification message factory. Extends {@link NotificationMessageFactory}. Contains factory methods for getting
 * ready email notification messages.
 *
 * @version 1.0.0
 */
@Service
public class EmailNotificationMessageFactory extends NotificationMessageFactory {

    /**
     * Determines whether ssl enabled.
     */
    @Value("${server.ssl.enabled}")
    private boolean sslEnabled;

    /**
     * Current server host name.
     */
    @Value("${server.hostname}")
    private String serverHostname;

    /**
     * Current server port.
     */
    @Value("${server.port}")
    private String serverPort;

    protected String getCurrentServerUrl() {
        return String.format("%s://%s",
                sslEnabled ? "https" : "http",
                serverHostname);
    }

    /**
     * Returns ready sign up {@link EmailNotificationMessage email notification message} object.
     *
     * @return email notification message for sign up.
     */
    public NotificationMessage createSignup(User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("link", String.format("%s/activate/%s",
                getCurrentServerUrl(),
                user.getLastVerificationToken().getToken()));

        return EmailNotificationMessage.builder()
                .template("signup.ftl")
                .subject("Anabel account activation")
                .message("We are glad to see in our service")
                .model(model)
                .build();
    }
}
