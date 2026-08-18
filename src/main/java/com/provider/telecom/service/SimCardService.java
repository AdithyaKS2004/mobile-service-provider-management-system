package com.provider.telecom.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.provider.telecom.dto.sim.SimActivationRequest;
import com.provider.telecom.dto.sim.SimResponse;
import com.provider.telecom.entity.SimCard;
import com.provider.telecom.entity.User;
import com.provider.telecom.enums.SimStatus;
import com.provider.telecom.repository.SimCardRepository;
import com.provider.telecom.repository.UserRepository;

@Service
public class SimCardService {

    private final SimCardRepository simCardRepository;
    private final UserRepository userRepository;

    public SimCardService(
            SimCardRepository simCardRepository,
            UserRepository userRepository) {

        this.simCardRepository = simCardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SimResponse requestActivation(
            String email,
            SimActivationRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (simCardRepository.existsByPhoneNumber(
                request.getPhoneNumber())) {

            throw new RuntimeException(
                    "Phone number is already registered");
        }

        if (simCardRepository.existsByImsiNumber(
                request.getImsiNumber())) {

            throw new RuntimeException(
                    "IMSI number is already registered");
        }

        SimCard simCard = new SimCard();

        simCard.setPhoneNumber(request.getPhoneNumber());
        simCard.setImsiNumber(request.getImsiNumber());
        simCard.setStatus(SimStatus.PENDING_KYC);
        simCard.setUser(user);

        SimCard savedSim =
                simCardRepository.save(simCard);

        return mapToResponse(savedSim);
    }

    @Transactional(readOnly = true)
    public List<SimResponse> getPendingSims() {

        return simCardRepository
                .findByStatus(SimStatus.PENDING_KYC)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public SimResponse approveSim(Long simId) {

        SimCard simCard =
                simCardRepository.findById(simId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "SIM not found"));

        if (simCard.getStatus() != SimStatus.PENDING_KYC) {

            throw new RuntimeException(
                    "Only pending SIMs can be approved");
        }

        simCard.setStatus(SimStatus.ACTIVE);

        SimCard savedSim =
                simCardRepository.save(simCard);

        return mapToResponse(savedSim);
    }

    @Transactional(readOnly = true)
    public List<SimResponse> getUserSims(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return simCardRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SimResponse mapToResponse(SimCard simCard) {

        return new SimResponse(
                simCard.getId(),
                simCard.getPhoneNumber(),
                simCard.getImsiNumber(),
                simCard.getStatus(),
                simCard.getUser().getId()
        );
    }
}