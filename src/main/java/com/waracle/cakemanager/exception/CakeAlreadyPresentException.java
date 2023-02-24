package com.waracle.cakemanager.exception;

public class CakeAlreadyPresentException extends RuntimeException {
    private String message;

    public CakeAlreadyPresentException(String message) {
        super(message);
        this.message = message;
    }

    public CakeAlreadyPresentException() {
    }
}
