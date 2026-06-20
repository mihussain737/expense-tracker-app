package com.expense_tracker.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                "Resource not found",
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);

    }
}
