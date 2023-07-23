package com.example.libraryupdater.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String status;
    private String messages;
    private Long timestamp;

    public ErrorResponse(String errorCode, String status, String messages) {
        this.errorCode = errorCode;
        this.status = status;
        this.messages = messages;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public ErrorResponse(String errorCode, String status) {
        this(errorCode, status, "");
    }
}
