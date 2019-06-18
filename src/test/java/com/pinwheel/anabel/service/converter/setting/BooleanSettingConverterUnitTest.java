package com.pinwheel.anabel.service.converter.setting;

import com.pinwheel.anabel.external.category.Unit;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
class BooleanSettingConverterUnitTest {
    private SettingConverter settingConverter = new BooleanSettingConverter();

    @Test
    void shouldConvertToJava() {
        assertEquals(settingConverter.convertToJava("true"), true);
        assertEquals(settingConverter.convertToJava("false"), false);
        assertEquals(settingConverter.convertToJava("unexpected"), false);
    }

    @Test
    void shouldConvertToDatabase() {
        assertEquals(settingConverter.convertToDatabase(true), "true");
        assertEquals(settingConverter.convertToDatabase(false), "false");
    }

    @Test
    void shouldThrowTypeMismatchException() {
        TypeMismatchException ex = assertThrows(
                TypeMismatchException.class,
                () -> settingConverter.convertToDatabase("true")
        );
        assertEquals(ex.getMessage(), "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Boolean'");
    }
}
