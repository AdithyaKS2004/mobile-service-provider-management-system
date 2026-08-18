package com.provider.telecom.dto.plan;

import com.provider.telecom.enums.PlanType;

import java.math.BigDecimal;

public class PlanResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private int validityDays;
    private double dataLimitGbPerDay;
    private int talktimeMins;
    private int smsCount;
    private PlanType planType;
    private boolean active;

    public PlanResponse() {
    }

    public PlanResponse(
            Long id,
            String name,
            BigDecimal price,
            int validityDays,
            double dataLimitGbPerDay,
            int talktimeMins,
            int smsCount,
            PlanType planType,
            boolean active) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.validityDays = validityDays;
        this.dataLimitGbPerDay = dataLimitGbPerDay;
        this.talktimeMins = talktimeMins;
        this.smsCount = smsCount;
        this.planType = planType;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getValidityDays() {
        return validityDays;
    }

    public double getDataLimitGbPerDay() {
        return dataLimitGbPerDay;
    }

    public int getTalktimeMins() {
        return talktimeMins;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public boolean isActive() {
        return active;
    }
}