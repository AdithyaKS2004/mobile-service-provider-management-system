package com.provider.telecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provider.telecom.dto.usage.UsageRequest;
import com.provider.telecom.dto.usage.UsageResponse;
import com.provider.telecom.service.UsageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sim")
public class UsageController {

    private final UsageService usageService;

    public UsageController(UsageService usageService) {
        this.usageService = usageService;
    }

    @PostMapping("/simulate-usage")
    public ResponseEntity<UsageResponse> simulateUsage(
            Authentication authentication,
            @Valid @RequestBody UsageRequest request) {

        return ResponseEntity.ok(
                usageService.simulateUsage(
                        authentication.getName(),
                        request
                )
        );
    }
}