package com.philldenness.cakemanager.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handleIllegalArgumentException() {
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("Returning internal server error due to exception", ex);
        return ResponseEntity.internalServerError().build();
    }
}