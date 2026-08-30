package com.provider.telecom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.provider.telecom.dto.plan.CreatePlanRequest;
import com.provider.telecom.dto.plan.PlanResponse;
import com.provider.telecom.entity.Plan;
import com.provider.telecom.exception.ResourceNotFoundException;
import com.provider.telecom.repository.PlanRepository;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<PlanResponse> getActivePlans() {

        return planRepository.findByActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PlanResponse> getAllPlans() {

        return planRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PlanResponse createPlan(CreatePlanRequest request) {

        Plan plan = new Plan();

        plan.setName(request.getName());
        plan.setPrice(request.getPrice());
        plan.setValidityDays(request.getValidityDays());
        plan.setDataLimitGbPerDay(request.getDataLimitGbPerDay());
        plan.setTalktimeMins(request.getTalktimeMins());
        plan.setSmsCount(request.getSmsCount());
        plan.setPlanType(request.getPlanType());

        // Newly created plans are active by default.
        plan.setActive(true);

        Plan savedPlan = planRepository.save(plan);

        return toResponse(savedPlan);
    }

    public PlanResponse updatePlan(
            Long planId,
            CreatePlanRequest request) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plan not found with id: " + planId
                        )
                );

        plan.setName(request.getName());
        plan.setPrice(request.getPrice());
        plan.setValidityDays(request.getValidityDays());
        plan.setDataLimitGbPerDay(request.getDataLimitGbPerDay());
        plan.setTalktimeMins(request.getTalktimeMins());
        plan.setSmsCount(request.getSmsCount());
        plan.setPlanType(request.getPlanType());

        Plan updatedPlan = planRepository.save(plan);

        return toResponse(updatedPlan);
    }

    public PlanResponse setPlanStatus(
            Long planId,
            boolean active) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plan not found with id: " + planId
                        )
                );

        plan.setActive(active);

        Plan updatedPlan = planRepository.save(plan);

        return toResponse(updatedPlan);
    }

    private PlanResponse toResponse(Plan plan) {

        return new PlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getPrice(),
                plan.getValidityDays(),
                plan.getDataLimitGbPerDay(),
                plan.getTalktimeMins(),
                plan.getSmsCount(),
                plan.getPlanType(),
                plan.isActive()
        );
    }
}