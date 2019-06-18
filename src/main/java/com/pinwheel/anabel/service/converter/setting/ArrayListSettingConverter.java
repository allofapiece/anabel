package com.pinwheel.anabel.service.converter.setting;

import org.springframework.beans.TypeMismatchException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 */
public class ArrayListSettingConverter implements SettingConverter {
    private static final String SEPARATOR = " ";

    @Override
    public Object convertToJava(String value) {
        return new ArrayList<>(Arrays.asList(value.split(SEPARATOR)));
    }

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
