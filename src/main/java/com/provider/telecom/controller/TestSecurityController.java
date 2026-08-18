package com.provider.telecom.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSecurityController {

    @GetMapping("/api/customer/test")
    public String customerEndpoint(Authentication authentication) {

        return "Customer access granted for: "
                + authentication.getName();
    }

    @GetMapping("/api/admin/test")
    public String adminEndpoint(Authentication authentication) {

        return "Admin access granted for: "
                + authentication.getName();
    }
}