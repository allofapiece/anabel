package com.pinwheel.anabel.service.converter.setting;

import org.springframework.beans.TypeMismatchException;

/**
 * Boolean converter. Converts {@code boolean} value to string for saving setting in database and represent string
 * value from database by {@code boolean} for using in code. Value can has {@code true} or {@code false} value.
 *
 * @version 1.0.0
 */
public class BooleanSettingConverter implements SettingConverter {
    /**
     * {@inheritDoc}
     */
    @Override
    public Object convertToJava(String value) {
        return Boolean.parseBoolean(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToDatabase(Object value) {
        if (!(value instanceof Boolean)) {
            throw new TypeMismatchException(value, Boolean.class);
        }

        return (boolean) value ? "true" : "false";
    }
}
