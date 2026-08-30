package com.provider.telecom.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.provider.telecom.dto.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class RestAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {

        ApiErrorResponse error =
                new ApiErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Authentication required"
                );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        objectMapper.writeValue(
                response.getOutputStream(),
                error
        );
    }
}