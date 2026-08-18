package com.provider.telecom.controller;

import com.provider.telecom.dto.sim.SimActivationRequest;
import com.provider.telecom.dto.sim.SimResponse;
import com.provider.telecom.service.SimCardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sim")
public class SimCardController {

    private final SimCardService simCardService;

    public SimCardController(SimCardService simCardService) {
        this.simCardService = simCardService;
    }

    @PostMapping("/activate")
    public ResponseEntity<SimResponse> activateSim(
            Authentication authentication,
            @Valid @RequestBody SimActivationRequest request) {

        SimResponse response =
                simCardService.requestActivation(
                        authentication.getName(),
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<SimResponse>> getMySims(
            Authentication authentication) {

        return ResponseEntity.ok(
                simCardService.getUserSims(
                        authentication.getName()
                )
        );
    }
}