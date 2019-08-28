package com.pinwheel.anabel.service.module.aws.s3;

/**
 * @version 1.0.0
 */
public class NotAllowedOperation extends RuntimeException {
    /**
     * Construct.
     *
     * @param message exception message.
     */
    public NotAllowedOperation(String message) {
        super(message);
    }

    /**
     * Construct.
     *
     * @param message
     * @param cause
     */
    public NotAllowedOperation(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct.
     *
     * @param cause
     */
    public NotAllowedOperation(Throwable cause) {
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
    protected NotAllowedOperation(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
