package com.pinwheel.anabel.controller.api.advice;

import com.pinwheel.anabel.service.converter.error.ErrorsConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;


@ControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorsConverter errorsConverter;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(Exception ex) {
        return new ResponseEntity<>(
                errorsConverter.convert(((ConstraintViolationException) ex).getConstraintViolations()),
                new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(RepositoryConstraintViolationException.class)
    public ResponseEntity<Object> constraintRepositoryViolationException(Exception ex) {
        return ResponseEntity.unprocessableEntity()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(errorsConverter.convert(((RepositoryConstraintViolationException) ex).getErrors()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return new ResponseEntity<>(
                errorsConverter.convert(ex.getBindingResult()),
                new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }
}
