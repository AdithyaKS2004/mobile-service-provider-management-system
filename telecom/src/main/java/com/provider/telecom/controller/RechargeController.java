package com.provider.telecom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provider.telecom.dto.recharge.RechargeRequest;
import com.provider.telecom.dto.subscription.SubscriptionResponse;
import com.provider.telecom.service.RechargeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recharge")
public class RechargeController {

    private final RechargeService rechargeService;

    public RechargeController(
            RechargeService rechargeService) {

        this.rechargeService = rechargeService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponse> recharge(
            Authentication authentication,
            @Valid @RequestBody RechargeRequest request) {

        SubscriptionResponse response =
                rechargeService.recharge(
                        authentication.getName(),
                        request.getSimCardId(),
                        request.getPlanId(),
                        request.getPaymentMethod()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}