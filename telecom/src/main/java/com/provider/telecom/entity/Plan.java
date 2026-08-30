package com.provider.telecom.entity;

import java.math.BigDecimal;

import com.provider.telecom.enums.PlanType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int validityDays;

    @Column(nullable = false)
    private double dataLimitGbPerDay;

    @Column(nullable = false)
    private int talktimeMins;

    @Column(nullable = false)
    private int smsCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType planType;

    @Column(nullable = false)
    private boolean active;

    public Plan() {
    }

    public Long getId() {
        return id;
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

    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
}