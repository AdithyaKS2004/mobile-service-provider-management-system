package com.provider.telecom.dto.auth;

import com.provider.telecom.enums.Role;

public class RegisterResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private Role role;

    public RegisterResponse() {
    }

    public RegisterResponse(
            Long id,
            String fullName,
            String email,
            String phone,
            Role role) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public Long getId() {
        return id;
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