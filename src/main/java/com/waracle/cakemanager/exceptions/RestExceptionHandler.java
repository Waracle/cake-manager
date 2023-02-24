package com.waracle.cakemanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        HttpStatus status = INTERNAL_SERVER_ERROR;
        String message = "An error occurred while processing your request.";

        if (ex instanceof ResponseStatusException exception) {
            status = HttpStatus.resolve(exception.getStatusCode().value());
            message = exception.getReason();
        } else if( ex instanceof AccessDeniedException) {
            status = FORBIDDEN;
            message = ex.getMessage();
        }

        // Create error response body
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(body, status);
    }
}

