package com.pinwheel.anabel.service.notification.factory;

import com.pinwheel.anabel.service.notification.domain.FlushNotificationMessage;
import com.pinwheel.anabel.service.notification.domain.FlushStatus;
import com.pinwheel.anabel.service.notification.domain.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Flush notification message factory. Extends {@link NotificationMessageFactory}. Contains factory methods for getting
 * ready flush notification messages.
 *
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class FlushNotificationMessageFactory extends NotificationMessageFactory {
    /**
     * Injection of {@link MessageSource} bean.
     */
    private final MessageSource messageSource;

    /**
     * Determines whether ssl enabled.
     */
    @Value("${server.ssl.enabled}")
    private boolean sslEnabled;

    /**
     * Current server address.
     */
    @Value("${server.hostname}")
    private String serverHostname;

    protected String getCurrentServerUrl() {
        return String.format("%s://%s%s",
                sslEnabled ? "https" : "http",
                serverHostname);
    }

    /**
     * Returns ready flush message with specific status and message.
     *
     * @param redirectAttributes
     * @param status
     * @param message
     * @return ready flush message.
     */
    public NotificationMessage createWithStatusAndMessage(
            RedirectAttributes redirectAttributes,
            FlushStatus status,
            String message
    ) {
        return FlushNotificationMessage.builder()
                .redirectAttributes(redirectAttributes)
                .status(status)
                .message(message)
                .build();
    }

    /**
     * Returns ready success flush message with specific message.
     *
     * @param redirectAttributes
     * @param message
     * @return ready success flush message.
     */
    public NotificationMessage createSuccess(RedirectAttributes redirectAttributes, String message) {
        return this.create("withStatusAndMessage", redirectAttributes, FlushStatus.SUCCESS, message);
    }

    /**
     * Returns ready verifying flush message.
     *
     * @return ready verifying flush message.
     */
    public NotificationMessage createVerifyYourEmail(
            RedirectAttributes redirectAttributes
    ) {
        return FlushNotificationMessage.builder()
                .status(FlushStatus.SUCCESS)
                .redirectAttributes(redirectAttributes)
                .message("auth.register.activation.verify")
                .build();
    }

    /**
     * Returns ready resend verification flush message.
     *
     * @return ready resend verification flush message.
     */
    public NotificationMessage createResendVerification(
            RedirectAttributes redirectAttributes,
            String oldToken
    ) {
        return FlushNotificationMessage.builder()
                .message("auth.register.activation.expired")
                .status(FlushStatus.DANGER)
                .redirectAttributes(redirectAttributes)
                .args(new Object[]{String.format("<a href=\"%s/reactivate/%s\">%s</a>",
                        getCurrentServerUrl(),
                        oldToken,
                        messageSource.getMessage(
                                "auth.register.activation.expired.link-phrase",
                                new Object[0],
                                LocaleContextHolder.getLocale()
                        ))})
                .build();
    }

    /**
     * Returns ready success activation flush message.
     *
     * @return ready success activation flush message.
     */
    public NotificationMessage createSuccessActivation(RedirectAttributes redirectAttributes) {
        return FlushNotificationMessage.builder()
                .message("auth.register.activation.success")
                .status(FlushStatus.SUCCESS)
                .redirectAttributes(redirectAttributes)
                .build();
    }
}
