package com.waracle.cakemgr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CakeNotFoundException extends ResponseStatusException {

    public CakeNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public CakeNotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }
}
