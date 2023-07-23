package com.example.libraryupdater.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        String errorCode = "ERR-004"; // Задайте общий код ошибки для всех случаев ResponseStatusException
        String status = "error";
        String message = ex.getReason() != null ? ex.getReason() : ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorCode, status, message);
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(ExceptionResponse.class)
    public ResponseEntity<ErrorResponse> handleCustomException(ExceptionResponse ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getStatus(), ex.getMessages());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
