package com.waracle.cakemgr.controller;


import com.waracle.cakemgr.exception.InvalidCakeException;
import com.waracle.manager.cake.model.ErrorDetails;
import com.waracle.manager.cake.model.JSONAPIErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(value = {InvalidCakeException.class, ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDetails handleBadRequest(final  Exception e){

        return getErrorDetails(HttpStatus.BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetails handleInternalServerError(final  Exception e){

        return getErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }

    private ErrorDetails getErrorDetails(final HttpStatus status, final String details){
        ErrorDetails errorDetails = new ErrorDetails();
        JSONAPIErrorDetails jsonapiErrorDetails = new JSONAPIErrorDetails()
                .code(status.value())
                .details(details)
                .message(details);
        errorDetails.addErrorsItem(jsonapiErrorDetails);
        return errorDetails;
    }
}
