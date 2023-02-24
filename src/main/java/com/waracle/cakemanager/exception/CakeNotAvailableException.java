package com.waracle.cakemanager.exception;

public class CakeNotAvailableException extends RuntimeException {
    private String message;

    public CakeNotAvailableException(String message) {
        super(message);
        this.message = message;
    }

    public CakeNotAvailableException() {
    }
}
