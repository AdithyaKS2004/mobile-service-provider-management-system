package com.provider.telecom.dto.subscription;

import java.time.LocalDate;

import com.provider.telecom.enums.SubscriptionStatus;

public class SubscriptionResponse {

    private Long id;
    private Long simCardId;
    private Long planId;
    private String planName;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private Double remainingDataMb;
    private Integer remainingTalktimeMins;
    private SubscriptionStatus status;

    public SubscriptionResponse() {
    }

    public SubscriptionResponse(
            Long id,
            Long simCardId,
            Long planId,
            String planName,
            LocalDate startDate,
            LocalDate expiryDate,
            Double remainingDataMb,
            Integer remainingTalktimeMins,
            SubscriptionStatus status) {

        this.id = id;
        this.simCardId = simCardId;
        this.planId = planId;
        this.planName = planName;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.remainingDataMb = remainingDataMb;
        this.remainingTalktimeMins = remainingTalktimeMins;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getSimCardId() {
        return simCardId;
    }

    public Long getPlanId() {
        return planId;
    }

    public String getPlanName() {
        return planName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Double getRemainingDataMb() {
        return remainingDataMb;
    }

    public Integer getRemainingTalktimeMins() {
        return remainingTalktimeMins;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }
}