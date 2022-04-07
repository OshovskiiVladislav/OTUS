package com.oshovskii.otus.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Object> handleException(AbstractException exception) {
        return new ResponseEntity<>(
                new MessageError(exception),
                exception.getHttpStatus());
    }
}
