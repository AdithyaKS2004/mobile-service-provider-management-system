package com.provider.telecom.dto.sim;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SimActivationRequest {

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;

    @NotBlank
    @Pattern(regexp = "^[0-9]{15}$")
    private String imsiNumber;

    public SimActivationRequest() {
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
}