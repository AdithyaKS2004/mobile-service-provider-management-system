package com.provider.telecom.entity;

import com.provider.telecom.enums.SimStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "sim_cards")
public class SimCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String imsiNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SimStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public SimCard() {
    }

    public Long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImsiNumber() {
        return imsiNumber;
    }

    public void setImsiNumber(String imsiNumber) {
        this.imsiNumber = imsiNumber;
    }

    public SimStatus getStatus() {
        return status;
    }

    public void setStatus(SimStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}