package com.provider.telecom.dto.plan;

import com.provider.telecom.enums.PlanType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreatePlanRequest {

    @NotBlank(message = "Plan name is required")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Price must be greater than zero")
    private BigDecimal price;

    @Positive(message = "Validity must be greater than zero")
    private int validityDays;

    @DecimalMin(value = "0.0", inclusive = false,
            message = "Data limit must be greater than zero")
    private double dataLimitGbPerDay;

    @Min(value = 0, message = "Talktime cannot be negative")
    private int talktimeMins;

    @Min(value = 0, message = "SMS count cannot be negative")
    private int smsCount;

    @NotNull(message = "Plan type is required")
    private PlanType planType;

    public CreatePlanRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(int validityDays) {
        this.validityDays = validityDays;
    }

    public double getDataLimitGbPerDay() {
        return dataLimitGbPerDay;
    }

    public void setDataLimitGbPerDay(double dataLimitGbPerDay) {
        this.dataLimitGbPerDay = dataLimitGbPerDay;
    }

    public int getTalktimeMins() {
        return talktimeMins;
    }

    public void setTalktimeMins(int talktimeMins) {
        this.talktimeMins = talktimeMins;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }
}