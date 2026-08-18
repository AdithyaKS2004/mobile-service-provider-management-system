package com.provider.telecom.controller;

import com.provider.telecom.dto.sim.SimResponse;
import com.provider.telecom.service.SimCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sims")
public class AdminSimController {

    private final SimCardService simCardService;

    public AdminSimController(SimCardService simCardService) {
        this.simCardService = simCardService;
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