package com.provider.telecom.dto.sim;

import com.provider.telecom.enums.SimStatus;

public class SimResponse {

    private Long id;
    private String phoneNumber;
    private String imsiNumber;
    private SimStatus status;
    private Long userId;

    public SimResponse() {
    }

    public SimResponse(
            Long id,
            String phoneNumber,
            String imsiNumber,
            SimStatus status,
            Long userId) {

        this.id = id;
        this.phoneNumber = phoneNumber;
        this.imsiNumber = imsiNumber;
        this.status = status;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getImsiNumber() {
        return imsiNumber;
    }

    public SimStatus getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }
}