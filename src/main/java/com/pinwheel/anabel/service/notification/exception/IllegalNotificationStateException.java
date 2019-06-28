package com.pinwheel.anabel.service.notification.exception;

import lombok.NoArgsConstructor;

/**
 * Exception which describes illegal notification state.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@NoArgsConstructor
public class IllegalNotificationStateException extends RuntimeException {
    /**
     * Construct.
     *
     * @param message exception message.
     */
    public IllegalNotificationStateException(String message) {
        super(message);
    }

    /**
     * Construct.
     *
     * @param message
     * @param cause
     */
    public IllegalNotificationStateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct.
     *
     * @param cause
     */
    public IllegalNotificationStateException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct.
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected IllegalNotificationStateException(String message, Throwable cause, boolean enableSuppression,
                                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
