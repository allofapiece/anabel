package com.pinwheel.anabel.service.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Notification message entity. Describes general notification message.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class NotificationMessage {
    /**
     * Notification message text.
     */
    protected String message;
}
