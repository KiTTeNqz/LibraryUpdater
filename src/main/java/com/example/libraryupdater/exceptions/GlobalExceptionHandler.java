package com.example.libraryupdater.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("ERR-004", "error",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExceptionResponse.class)
    public ResponseEntity<ErrorResponse> handleCustomException(ExceptionResponse ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getStatus(), ex.getMessages());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
