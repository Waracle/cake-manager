package com.waracle.cakemgr.exception;

public class CakeManagerException extends RuntimeException{
    public CakeManagerException(String customMessage, Throwable cause){
        super(customMessage, cause);
    }

    public CakeManagerException(String message){
        super(message);
    }
}
