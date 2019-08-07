package com.pinwheel.anabel.util;

import com.pinwheel.anabel.external.category.Unit;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Category(Unit.class)
class ConvertUtilsUnitTest {

    @Test
    void shouldConvertEnumToOptionsMap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, String> map = Map.of(
                "FIRST_VALUE", "FirstValue",
                "SECOND_VALUE", "SecondValue"
        );
        assertEquals(map, ConvertUtils.enumToOptions(TestEnumWithOption.class));

        Map<String, String> map2 = Map.of(
                "FIRST_VALUE", "FIRST_VALUE",
                "SECOND_VALUE", "SECOND_VALUE"
        );
        assertEquals(map2, ConvertUtils.enumToOptions(TestEnumWithoutOption.class));
    }

    @Test
    void shouldConvertEnumValuesToOptionsMap() {
        Map<String, String> map = Map.of(
                "FIRST_VALUE", "FirstValue",
                "SECOND_VALUE", "SecondValue"
        );
        assertEquals(map, ConvertUtils.enumValuesToOptions(
                List.of(TestEnumWithOption.FIRST_VALUE, TestEnumWithOption.SECOND_VALUE))
        );
        assertEquals(map, ConvertUtils.enumValuesToOptions(
                new Object[]{TestEnumWithOption.FIRST_VALUE, TestEnumWithOption.SECOND_VALUE}
        ));

        Map<String, String> map2 = Map.of(
                "FIRST_VALUE", "FIRST_VALUE",
                "SECOND_VALUE", "SECOND_VALUE"
        );
        assertEquals(map2, ConvertUtils.enumValuesToOptions(
                List.of(TestEnumWithoutOption.FIRST_VALUE, TestEnumWithoutOption.SECOND_VALUE)
        ));
        assertEquals(map2, ConvertUtils.enumValuesToOptions(
                new Object[]{TestEnumWithoutOption.FIRST_VALUE, TestEnumWithoutOption.SECOND_VALUE}
        ));
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
