package com.provider.telecom.controller;

import com.provider.telecom.dto.plan.CreatePlanRequest;
import com.provider.telecom.dto.plan.PlanResponse;
import com.provider.telecom.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/plans")
    public ResponseEntity<List<PlanResponse>> getActivePlans() {

        return ResponseEntity.ok(
                planService.getActivePlans()
        );
    }

    @GetMapping("/admin/plans")
    public ResponseEntity<List<PlanResponse>> getAllPlans() {

        return ResponseEntity.ok(
            planService.getAllPlans()
        );
    }

    @PostMapping("/admin/plans")
    public ResponseEntity<PlanResponse> createPlan(
            @Valid @RequestBody CreatePlanRequest request) {

        PlanResponse response =
                planService.createPlan(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/admin/plans/{planId}")
    public ResponseEntity<PlanResponse> updatePlan(
            @PathVariable Long planId,
            @Valid @RequestBody CreatePlanRequest request) {

        return ResponseEntity.ok(
                planService.updatePlan(planId, request)
        );
    }

    @PatchMapping("/admin/plans/{planId}/status")
    public ResponseEntity<PlanResponse> updatePlanStatus(
            @PathVariable Long planId,
            @RequestParam boolean active) {

        return ResponseEntity.ok(
                planService.setPlanStatus(planId, active)
        );
    }
}