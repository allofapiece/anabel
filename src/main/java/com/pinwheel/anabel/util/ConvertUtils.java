package com.pinwheel.anabel.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Convert utils. Static helper methods for converting.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public class ConvertUtils {
    /**
     * Converts enum to {@link Map} for displaying it in select html tags.
     * <p>
     * Key of the map will be result of invoking {@code getOption} method of passed enum. If this method does not
     * exist {@code toString} method will be invoked instead.
     * <p>
     * Values of the map will be enums names represented by {@link Object#toString()} method.
     *
     * @param clazz class of enum. If clazz does not enum null will be returned.
     * @return map of converted enum.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, String> enumToOptions(Class clazz)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (!clazz.isEnum()) {
            return null;
        }

        Method valuesMethod = clazz.getDeclaredMethod("values");

        if (valuesMethod == null) {
            return null;
        }

        return ConvertUtils.enumValuesToOptions((Object[]) valuesMethod.invoke(null));
    }

    /**
     * Overloaded variant for {@link ConvertUtils#enumValuesToOptions(List)} for array of values.
     *
     * @param values enum values for representing passed by array.
     * @return map of converted values.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, String> enumValuesToOptions(Object[] values) {
        return ConvertUtils.enumValuesToOptions(Arrays.asList(values));
    }

    /**
     * Converts enum values to {@link Map} for displaying it in select html tags.
     * <p>
     * Keys of the map will be result of invoking {@code getOption} method of passed enum. If this method does not
     * exist {@code toString} method will be invoked instead.
     * <p>
     * Values of the map will be enums names represented by {@link Object#toString()} method.
     *
     * @param values enum values for representing.
     * @return map of converted values.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, String> enumValuesToOptions(List<Object> values) {
        Collector<Object, ?, Map<String, String>> collector = Collectors.toMap(
                Object::toString,
                ConvertUtils::enumToOption
        );

        return values.stream().collect(collector);
    }

    /**
     * Converts enum to string by invoking {@code getOption} method. If enum has not this method,
     * {@link Object#toString()} will be user instead.
     *
     * @param obj enum for converting.
     * @return result of converting.
     */
    public static String enumToOption(Object obj) {
        try {
            Method getOptionMethod = obj.getClass().getDeclaredMethod("getOption");

            if (getOptionMethod != null) {
                return (String) getOptionMethod.invoke(obj);
            }
        } catch (ReflectiveOperationException e) {
        }

        return obj.toString();
    }
}
