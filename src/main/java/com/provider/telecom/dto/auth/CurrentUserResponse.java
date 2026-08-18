package com.provider.telecom.dto.auth;

import com.provider.telecom.enums.Role;

public class CurrentUserResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;

    public CurrentUserResponse() {
    }

    public CurrentUserResponse(
            Long userId,
            String fullName,
            String email,
            String phone,
            Role role) {

        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
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

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }
}