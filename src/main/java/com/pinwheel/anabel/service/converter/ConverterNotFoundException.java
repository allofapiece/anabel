package com.pinwheel.anabel.service.converter;

import org.springframework.core.NestedRuntimeException;

/**
 * Exception which can be throw in cases, when converters cannot be found.
 *
 * @version 1.0.0
 */
public class ConverterNotFoundException extends NestedRuntimeException {
    /**
     * Default constructor. Sets default message for exception.
     */
    public ConverterNotFoundException() {
        this("Converter not found.");
    }

    /**
     * Constructor. Sets message.
     *
     * @param msg message for exception.
     */
    public ConverterNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructor. Sets message and another wrapped exception.
     *
     * @param msg   message for exception.
     * @param cause wrapped exception.
     */
    public ConverterNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
