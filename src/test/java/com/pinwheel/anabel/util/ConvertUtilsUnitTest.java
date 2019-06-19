package com.pinwheel.anabel.util;

import com.pinwheel.anabel.external.category.Unit;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = {
        "/application-test.properties",
        "/application-test-local.properties"
})
@Category(Unit.class)
class ConvertUtilsUnitTest {

    @Test
    void shouldConvertEnumToOptionsMap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, TestEnumWithOption> map = Map.of(
                "FirstValue", TestEnumWithOption.FIRST_VALUE,
                "SecondValue", TestEnumWithOption.SECOND_VALUE
        );
        assertEquals(map, ConvertUtils.enumOptions(TestEnumWithOption.class));

        Map<String, TestEnumWithoutOption> map2 = Map.of(
                "FIRST_VALUE", TestEnumWithoutOption.FIRST_VALUE,
                "SECOND_VALUE", TestEnumWithoutOption.SECOND_VALUE
        );
        assertEquals(map2, ConvertUtils.enumOptions(TestEnumWithoutOption.class));
    }

    private enum TestEnumWithOption {
        FIRST_VALUE("FirstValue"),
        SECOND_VALUE("SecondValue");

        String option;

        TestEnumWithOption(String option) {
            this.option = option;
        }

        String getOption() {
            return this.option;
        }
    }

    private enum TestEnumWithoutOption {
        FIRST_VALUE,
        SECOND_VALUE;
    }
}
