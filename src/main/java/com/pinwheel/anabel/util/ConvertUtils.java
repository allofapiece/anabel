package com.pinwheel.anabel.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
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
}
