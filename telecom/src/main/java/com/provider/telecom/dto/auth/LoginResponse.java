package com.provider.telecom.dto.auth;

import com.provider.telecom.enums.Role;

public class LoginResponse {

    private String message;
    private Long userId;
    private String fullName;
    private String email;
    private Role role;

    public LoginResponse() {
    }

    public LoginResponse(
            String message,
            Long userId,
            String fullName,
            String email,
            Role role) {

        this.message = message;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}