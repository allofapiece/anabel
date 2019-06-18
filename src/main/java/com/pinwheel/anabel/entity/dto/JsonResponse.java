package com.pinwheel.anabel.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @version 1.0.0
 */
@NoArgsConstructor
@Data
public class JsonResponse<T> {
    private List<FieldError> fieldErrors;
    private T entity;

    public JsonResponse(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public JsonResponse(T entity) {
        this.entity = entity;
    }

    public JsonResponse(T entity, List<FieldError> fieldErrors) {
        this(fieldErrors);
        this.entity = entity;
    }
}
