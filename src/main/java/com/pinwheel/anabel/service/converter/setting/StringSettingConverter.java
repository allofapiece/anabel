package com.pinwheel.anabel.service.converter.setting;

import org.springframework.beans.TypeMismatchException;

/**
 * @version 1.0.0
 */
public class StringSettingConverter implements SettingConverter {
    @Override
    public Object convertToJava(String value) {
        return value;
    }

    @Override
    public String convertToDatabase(Object value) {
        if (!(value instanceof String)) {
            throw new TypeMismatchException(value, String.class);
        }

        return (String) value;
    }
}
