package com.pinwheel.anabel.service.converter.setting;

import org.springframework.beans.TypeMismatchException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Array list converter. Converts {@link ArrayList} to string for saving setting in database and represent string value
 * from database by {@link ArrayList} for using in code.
 *
 * @version 1.0.0
 */
public class ArrayListSettingConverter implements SettingConverter {
    /**
     * String for separating values.
     */
    private static final String SEPARATOR = " ";

    /**
     * {@inheritDoc}
     */
    @Override
    public Object convertToJava(String value) {
        return new ArrayList<>(Arrays.asList(value.split(SEPARATOR)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToDatabase(Object value) {
        if (!(value instanceof ArrayList)) {
            throw new TypeMismatchException(value, ArrayList.class);
        }

        return ((ArrayList<String>) value)
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(SEPARATOR));
    }
}
