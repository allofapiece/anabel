package com.pinwheel.anabel.service.converter.setting;

import com.pinwheel.anabel.external.category.Unit;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.TypeMismatchException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Category(Unit.class)
class ArrayListSettingConverterUnitTest {
    private SettingConverter settingConverter = new ArrayListSettingConverter();

    @Test
    void shouldConvertToJava() {
        assertEquals(settingConverter.convertToJava("test value"), new ArrayList<>(Arrays.asList("test", "value")));
    }

    @Test
    void shouldConvertToDatabase() {
        List<String> list = new ArrayList<>(Arrays.asList("test", "value"));
        assertEquals(settingConverter.convertToDatabase(list), "test value");
    }

    @Test
    void shouldThrowTypeMismatchException() {
        TypeMismatchException ex = assertThrows(
                TypeMismatchException.class,
                () -> settingConverter.convertToDatabase(true)
        );
        assertEquals(ex.getMessage(), "Failed to convert value of type 'java.lang.Boolean' to required type 'java.util.ArrayList'");
    }
}
