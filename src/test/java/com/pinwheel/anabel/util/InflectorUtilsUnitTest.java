package com.pinwheel.anabel.util;

import com.pinwheel.anabel.external.category.Unit;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
class InflectorUtilsUnitTest {

    @Test
    void shouldConvertFromOneFormatToAnother() {
        assertEquals("test-value", InflectorUtils.camel2Hyphen("TestValue", false));
        assertEquals("test-value", InflectorUtils.camel2Hyphen("testValue"));
        assertEquals("TestValue", InflectorUtils.hyphen2Camel("test-value", false));
        assertEquals("testValue", InflectorUtils.hyphen2Camel("test-value"));
        assertEquals("TEST_VALUE", InflectorUtils.camel2Underscore("TestValue", false, false));
        assertEquals("test_value", InflectorUtils.camel2Underscore("TestValue", false));
        assertEquals("test_value", InflectorUtils.camel2Underscore("testValue"));
        assertEquals("TestValue", InflectorUtils.underscore2Camel("TEST_VALUE", false, false));
        assertEquals("testValue", InflectorUtils.underscore2Camel("TEST_VALUE", false));
        assertEquals("testValue", InflectorUtils.underscore2Camel("test_value"));
    }
}
