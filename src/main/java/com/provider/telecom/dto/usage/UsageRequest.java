package com.provider.telecom.dto.usage;

import com.provider.telecom.enums.UsageType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UsageRequest {

    @NotNull
    private Long simCardId;

    @NotNull
    private UsageType usageType;

    @NotNull
    @Positive
    private Double amount;

    public UsageRequest() {
    }

    public Long getSimCardId() {
        return simCardId;
    }

    public void setSimCardId(Long simCardId) {
        this.simCardId = simCardId;
    }

    public UsageType getUsageType() {
        return usageType;
    }

    public void setUsageType(UsageType usageType) {
        this.usageType = usageType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}