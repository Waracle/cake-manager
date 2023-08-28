package com.philldenness.cakemanager.controller;

import com.philldenness.cakemanager.metrics.CounterManager;
import com.philldenness.cakemanager.metrics.CounterName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final CounterManager counterManager;

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handleIllegalArgumentException() {
        counterManager.increment(CounterName.BAD_REQUEST);
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("Returning internal server error due to exception", ex);
        counterManager.increment(CounterName.UNKNOWN_ERROR);
        return ResponseEntity.internalServerError().build();
    }
}