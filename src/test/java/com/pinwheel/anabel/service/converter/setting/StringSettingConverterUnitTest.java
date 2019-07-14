package com.pinwheel.anabel.service.converter.setting;

import com.pinwheel.anabel.external.category.Unit;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
class StringSettingConverterUnitTest {
    private SettingConverter settingConverter = new StringSettingConverter();

    @Test
    void shouldConvertToJava() {
        assertEquals(settingConverter.convertToJava("test value"), "test value");
    }

    @Test
    void shouldConvertToDatabase() {
        assertEquals(settingConverter.convertToDatabase("test value"), "test value");
    }

    @Test
    void shouldThrowTypeMismatchException() {
        TypeMismatchException ex = assertThrows(
                TypeMismatchException.class,
                () -> settingConverter.convertToDatabase(true)
        );
        assertEquals(ex.getMessage(), "Failed to convert value of type 'java.lang.Boolean' to required type 'java.lang.String'");
    }
}
