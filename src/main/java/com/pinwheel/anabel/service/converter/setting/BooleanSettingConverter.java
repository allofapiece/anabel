package com.pinwheel.anabel.service.converter.setting;

import org.springframework.beans.TypeMismatchException;

/**
 * @version 1.0.0
 */
public class BooleanSettingConverter implements SettingConverter {
    @Override
    public Object convertToJava(String value) {
        return Boolean.parseBoolean(value);
    }

    @Override
    public String convertToDatabase(Object value) {
        if (!(value instanceof Boolean)) {
            throw new TypeMismatchException(value, Boolean.class);
        }

        return (boolean) value ? "true" : "false";
    }
}
