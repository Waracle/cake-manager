package com.waracle.cakemgr.exception;

public class InvalidCakeException extends RuntimeException  {
    public InvalidCakeException(String message) {
        super(message);
    }

    public InvalidCakeException(String message, Throwable cause) {
        super(message, cause);
    }
}
