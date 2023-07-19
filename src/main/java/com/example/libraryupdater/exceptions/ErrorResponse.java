package com.example.libraryupdater.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String messages;
    private Long timestamp;
    private String status;
    public ErrorResponse(String error, String status, String message) {
        this.errorCode = error;
        this.messages = message;
        this.timestamp = Instant.now().toEpochMilli();
        this.status = status;
    }
    public ErrorResponse(String error, String status) {
        this.errorCode = error;
        this.messages = "";
        this.timestamp = Instant.now().toEpochMilli();
        this.status = status;
    }
}