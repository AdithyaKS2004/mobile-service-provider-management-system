package com.provider.telecom.exception;

public class ResourceAccessDeniedException extends RuntimeException {

    public ResourceAccessDeniedException(String message) {
        super(message);
    }
}