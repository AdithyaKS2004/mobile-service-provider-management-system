package com.provider.telecom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provider.telecom.dto.subscription.SubscriptionResponse;
import com.provider.telecom.service.RechargeService;

@RestController
@RequestMapping("/api/user/subscriptions")
public class SubscriptionController {

    private final RechargeService rechargeService;

    public SubscriptionController(
            RechargeService rechargeService) {

        this.rechargeService = rechargeService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>>
    getSubscriptions(Authentication authentication) {

        return ResponseEntity.ok(
                rechargeService.getUserSubscriptions(
                        authentication.getName()
                )
        );
    }
}