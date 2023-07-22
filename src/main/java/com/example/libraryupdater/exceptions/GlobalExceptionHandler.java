package com.example.libraryupdater.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;


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

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> handleResponseException(WebClientResponseException ex) {
        ErrorResponse errorResponse = ex.getResponseBodyAs(ErrorResponse.class);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<ErrorResponse> handleRequestException(WebClientRequestException ex) {
        ExceptionResponse exceptionResponse = (ExceptionResponse) ex.getCause();
        ErrorResponse errorResponse = new ErrorResponse(exceptionResponse.getErrorCode(), exceptionResponse.getStatus(), exceptionResponse.getMessages());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
