package com.provider.telecom.entity;

import com.provider.telecom.enums.SubscriptionStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sim_card_id", nullable = false)
    private SimCard simCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private double remainingDataMb;

    @Column(nullable = false)
    private int remainingTalktimeMins;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    public Subscription() {
    }

    public Long getId() {
        return id;
    }

    public SimCard getSimCard() {
        return simCard;
    }

    public void setSimCard(SimCard simCard) {
        this.simCard = simCard;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getRemainingDataMb() {
        return remainingDataMb;
    }

    public void setRemainingDataMb(double remainingDataMb) {
        this.remainingDataMb = remainingDataMb;
    }

    public int getRemainingTalktimeMins() {
        return remainingTalktimeMins;
    }

    public void setRemainingTalktimeMins(int remainingTalktimeMins) {
        this.remainingTalktimeMins = remainingTalktimeMins;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }
}