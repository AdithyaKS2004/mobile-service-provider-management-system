package com.provider.telecom.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.provider.telecom.dto.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class RestAccessDeniedHandler
        implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException {

        ApiErrorResponse error =
                new ApiErrorResponse(
                        HttpStatus.FORBIDDEN.value(),
                        "You do not have permission to access this resource"
                );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        objectMapper.writeValue(
                response.getOutputStream(),
                error
        );
    }
}