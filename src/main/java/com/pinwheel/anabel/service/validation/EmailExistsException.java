package com.pinwheel.anabel.service.validation;

/**
 * Exception for email already existing validation error.
 *
 * @version 1.0
 */
@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {

    /**
     * Constructor.
     *
     * @param message exception message.
     */
    public EmailExistsException(final String message) {
        super(message);
    }
}
