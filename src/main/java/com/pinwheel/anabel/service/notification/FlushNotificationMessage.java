package com.pinwheel.anabel.service.notification;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;

/**
 * Flush Notification Message. Represents bootstrap alert messages.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FlushNotificationMessage extends NotificationMessage {
    /**
     * Flush status. Represents
     */
    private FlushStatus status = FlushStatus.INFO;

    /**
     * View model for filling template.
     */
    private Model model;

    /**
     * Constructor.
     *
     * @param message message text.
     * @param status status.
     * @param model view model.
     */
    @Builder
    public FlushNotificationMessage(String message, FlushStatus status, Model model) {
        super(message);
        this.status = status;
        this.model = model;
    }
}
