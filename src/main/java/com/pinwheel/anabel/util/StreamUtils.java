package com.pinwheel.anabel.util;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream utils. Contains static methods for streams.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public class StreamUtils {
    /**
     * Reverses passed stream.
     *
     * @param input stream to reversing.
     * @param <T>   type of stream.
     * @return reversed stream.
     */
    @SuppressWarnings("unchecked")
    public static <T> Stream<T> reverse(Stream<T> input) {
        Object[] temp = input.toArray();

        return (Stream<T>) IntStream.range(0, temp.length)
                .mapToObj(i -> temp[temp.length - i - 1]);
    }
}
