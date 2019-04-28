package com.pinwheel.anabel.service.notification;

import lombok.*;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Email notification message. Extends {@link NotificationMessage} class.
 *
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmailNotificationMessage extends NotificationMessage {
    /**
     * Represents multi part mode of {@link MimeMessageHelper} class.
     * {@link MimeMessageHelper#MULTIPART_MODE_MIXED_RELATED MULTIPART_MODE_MIXED_RELATED} by default.
     */
    private int multiPartMode = MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

    /**
     * Represents using charset. {@link StandardCharsets#UTF_8} by default.
     */
    private Charset charset = StandardCharsets.UTF_8;

    /**
     * Subject of the mail.
     */
    private String subject;

    /**
     * Target email.
     */
    private String to;

    /**
     * Server from which letters are sent.
     */
    private String from;

    /**
     * Letter template.
     */
    private String template;

    /**
     * Model for filling templates by view engine.
     */
    @Singular
    private Map<String, Object> model = new HashMap<>();

    /**
     * Constructor.
     *
     * @param message
     * @param multiPartMode
     * @param charset
     * @param subject
     * @param to
     * @param from
     * @param template
     * @param model
     */
    @Builder
    public EmailNotificationMessage(String message, int multiPartMode, Charset charset, String subject,
                                    String to, String from, String template, Map<String, Object> model) {
        super(message);
        this.message = message;
        this.multiPartMode = multiPartMode == 0 ? MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED : multiPartMode;
        this.charset = charset == null ? StandardCharsets.UTF_8 : charset;
        this.subject = subject;
        this.to = to;
        this.from = from;
        this.template = template;
        this.model = model != null ? model : new HashMap<>();
    }
}
