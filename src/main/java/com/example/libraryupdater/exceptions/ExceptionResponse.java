package com.example.libraryupdater.exceptions;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ExceptionResponse extends Exception{
    private String errorCode;

    private String messages;
    private Long timestamp;
    private String status;

    public ExceptionResponse(String error, String status, String message) {
        super(message);
        this.errorCode = error;
        this.timestamp = Instant.now().toEpochMilli();
        this.status = status;
        this.messages = message;
    }

    public ExceptionResponse(String error, String status) {
        this.errorCode = error;
        this.messages = "";
        this.timestamp = Instant.now().toEpochMilli();
        this.status = status;
    }
}
