package com.waracle.cakemanager.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Value(value = "${cake.notavailable.message}")
    private String notAvailableMessage;
    @Value(value = "${cake.exists.message}")
    private String alreadyExistsMessage;
    @Value(value = "${cake.exception.message}")
    private String connectionException;

    @ExceptionHandler(value = CakeNotAvailableException.class)
    public ResponseEntity cakeNotAvailableException(CakeNotAvailableException cakeNotAvailableException) {
        return new ResponseEntity<String>(notAvailableMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CakeAlreadyPresentException.class)
    public ResponseEntity cakeAlreadyPresentException(CakeAlreadyPresentException cakeAlreadyPresentException) {
        return new ResponseEntity<String>(alreadyExistsMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> databaseConnectionFailsException(Exception exception) {
        return new ResponseEntity<>(connectionException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
