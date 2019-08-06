package com.pinwheel.anabel.util;

import com.pinwheel.anabel.external.category.Unit;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Category(Unit.class)
class StreamUtilsUnitTest {

    @Test
    void shouldReverseStream() {
        var reversedStream = StreamUtils.reverse(Stream.of("one", "two", "three"));
        assertEquals("three", reversedStream.collect(Collectors.toList()).get(0));
    }
}
