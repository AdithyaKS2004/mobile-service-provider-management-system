package com.provider.telecom.dto;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    @SuppressWarnings("FieldMayBeFinal")
    private int status;
    @SuppressWarnings("FieldMayBeFinal")
    private String message;
    @SuppressWarnings("FieldMayBeFinal")
    private LocalDateTime timestamp;

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}