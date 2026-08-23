package com.provider.telecom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provider.telecom.dto.sim.SimCreateRequest;
import com.provider.telecom.dto.sim.SimResponse;
import com.provider.telecom.service.SimCardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/sims")
public class AdminSimController {

    private final SimCardService simCardService;

    public AdminSimController(SimCardService simCardService) {
        this.simCardService = simCardService;
    }

    @PostMapping
    public ResponseEntity<SimResponse> createSim(
            @Valid @RequestBody SimCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(simCardService.createSim(request));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<SimResponse>> getPendingSims() {

        return ResponseEntity.ok(
                simCardService.getPendingSims()
        );
    }

    @PatchMapping("/{simId}/approve")
    public ResponseEntity<SimResponse> approveSim(
            @PathVariable Long simId) {

        return ResponseEntity.ok(
                simCardService.approveSim(simId)
        );
    }
}