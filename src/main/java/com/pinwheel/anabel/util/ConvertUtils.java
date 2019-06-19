package com.pinwheel.anabel.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
     * Converts {@link BindingResult} object to the map of errors for displaying.
     *
     * @param bindingResult binding result.
     * @return map of errors.
     */
    public static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );

        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    /**
     * Converts enum to {@link Map} for displaying it in select html tags.
     * <p>
     * Key of the map will be result of invoking
     * {@code getOption} method of passed enum. If this method does not exist {@code toString} method will be invoked
     * instead.
     * <p>
     * Value of the map will be enum instance.
     *
     * @param clazz class of enum. If clazz does not enum null will be returned.
     * @return map of converted enum.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> enumOptions(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (!clazz.isEnum()) {
            return null;
        }

        Method valuesMethod = clazz.getDeclaredMethod("values");

        if (valuesMethod == null) {
            return null;
        }

        Object[] values = (Object[]) valuesMethod.invoke(null);

        Collector<Object, ?, Map<String, Object>> collector = Collectors.toMap(
                val -> {
                    try {
                        Method getOptionMethod = val.getClass().getDeclaredMethod("getOption");

                        if (getOptionMethod != null) {
                            return (String) getOptionMethod.invoke(val);
                        }
                    } catch (ReflectiveOperationException e) {
                    }

                    return val.toString();
                },
                val -> val
        );

        return Arrays.stream(values).collect(collector);
    }
}
