package com.provider.telecom.dto.recharge;

import jakarta.validation.constraints.NotNull;

public class RechargeRequest {

    @NotNull
    private Long simCardId;

    @NotNull
    private Long planId;

    @NotNull
    private String paymentMethod;

    public RechargeRequest() {
    }

    public Long getSimCardId() {
        return simCardId;
    }

    public void setSimCardId(Long simCardId) {
        this.simCardId = simCardId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}