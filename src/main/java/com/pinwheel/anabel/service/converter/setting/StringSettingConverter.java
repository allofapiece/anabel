package com.pinwheel.anabel.service.converter.setting;

import org.springframework.beans.TypeMismatchException;

/**
 * String converter. Returns string from database as it is and save it in database as simple string.
 *
 * @version 1.0.0
 */
public class StringSettingConverter implements SettingConverter {
    /**
     * {@inheritDoc}
     */
    @Override
    public Object convertToJava(String value) {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToDatabase(Object value) {
        if (!(value instanceof String)) {
            throw new TypeMismatchException(value, String.class);
        }

        return (String) value;
    }
}
