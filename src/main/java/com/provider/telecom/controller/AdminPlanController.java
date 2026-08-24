// package com.provider.telecom.controller;

// import com.provider.telecom.dto.plan.CreatePlanRequest;
// import com.provider.telecom.dto.plan.PlanResponse;
// import com.provider.telecom.service.PlanService;
// import jakarta.validation.Valid;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/admin/plans")
// public class AdminPlanController {

//     private final PlanService planService;

//     public AdminPlanController(PlanService planService) {
//         this.planService = planService;
//     }

//     @PostMapping
//     public ResponseEntity<PlanResponse> createPlan(
//             @Valid @RequestBody CreatePlanRequest request) {

//         return ResponseEntity
//                 .status(HttpStatus.CREATED)
//                 .body(planService.createPlan(request));
//     }

//     @PutMapping("/{planId}")
//     public ResponseEntity<PlanResponse> updatePlan(
//             @PathVariable Long planId,
//             @Valid @RequestBody CreatePlanRequest request) {

//         return ResponseEntity.ok(
//                 planService.updatePlan(planId, request)
//         );
//     }

//     @PatchMapping("/{planId}/status")
//     public ResponseEntity<PlanResponse> setPlanStatus(
//             @PathVariable Long planId,
//             @RequestParam boolean active) {

//         return ResponseEntity.ok(
//                 planService.setPlanStatus(planId, active)
//         );
//     }
// }