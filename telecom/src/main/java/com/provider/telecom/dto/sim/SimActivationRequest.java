package com.provider.telecom.dto.sim;

import jakarta.validation.constraints.NotNull;

public class SimActivationRequest {

    @NotNull
    private Long simCardId;

    public SimActivationRequest() {
    }

    public Long getSimCardId() {
        return simCardId;
    }

    public void setSimCardId(Long simCardId) {
        this.simCardId = simCardId;
    }
}