package com.provider.telecom.dto.usage;

import com.provider.telecom.enums.UsageType;

public class UsageResponse {

    private Long subscriptionId;
    private UsageType usageType;
    private Double usedAmount;
    private Double remainingDataMb;
    private Integer remainingTalktimeMins;

    public UsageResponse() {
    }

    public UsageResponse(
            Long subscriptionId,
            UsageType usageType,
            Double usedAmount,
            Double remainingDataMb,
            Integer remainingTalktimeMins) {

        this.subscriptionId = subscriptionId;
        this.usageType = usageType;
        this.usedAmount = usedAmount;
        this.remainingDataMb = remainingDataMb;
        this.remainingTalktimeMins =
                remainingTalktimeMins;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public UsageType getUsageType() {
        return usageType;
    }

    public Double getUsedAmount() {
        return usedAmount;
    }

    public Double getRemainingDataMb() {
        return remainingDataMb;
    }

    public Integer getRemainingTalktimeMins() {
        return remainingTalktimeMins;
    }
}