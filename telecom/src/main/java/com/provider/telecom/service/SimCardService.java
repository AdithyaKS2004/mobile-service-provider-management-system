package com.provider.telecom.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.provider.telecom.dto.sim.SimActivationRequest;
import com.provider.telecom.dto.sim.SimCreateRequest;
import com.provider.telecom.dto.sim.SimResponse;
import com.provider.telecom.entity.SimCard;
import com.provider.telecom.entity.User;
import com.provider.telecom.enums.SimStatus;
import com.provider.telecom.exception.BusinessException;
import com.provider.telecom.exception.ResourceAccessDeniedException;
import com.provider.telecom.exception.ResourceAlreadyExistsException;
import com.provider.telecom.exception.ResourceNotFoundException;
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
    public SimResponse createSim(SimCreateRequest request) {

        if (simCardRepository.existsByPhoneNumber(
                request.getPhoneNumber())) {

            throw new ResourceAlreadyExistsException(
                    "Phone number is already registered");
        }

        if (simCardRepository.existsByImsiNumber(
                request.getImsiNumber())) {

            throw new ResourceAlreadyExistsException(
                    "IMSI number is already registered");
        }

        SimCard simCard = new SimCard();

        simCard.setPhoneNumber(request.getPhoneNumber());
        simCard.setImsiNumber(request.getImsiNumber());
        simCard.setStatus(SimStatus.AVAILABLE);
        simCard.setUser(null);

        SimCard savedSim =
                simCardRepository.save(simCard);

        return mapToResponse(savedSim);
    }

    @Transactional(readOnly = true)
    public List<SimResponse> getAvailableSims() {

        return simCardRepository
                .findByStatus(SimStatus.AVAILABLE)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public SimResponse requestActivation(
            String email,
            SimActivationRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        SimCard simCard = simCardRepository
                .findByIdWithLock(request.getSimCardId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "SIM not found"));

        if (simCard.getStatus() != SimStatus.AVAILABLE) {

            throw new BusinessException(
                    "SIM is not available for activation");
        }

        if (simCard.getUser() != null) {

            throw new ResourceAccessDeniedException(
                    "SIM is already assigned to another customer");
        }

        simCard.setUser(user);
        simCard.setStatus(SimStatus.PENDING_KYC);

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
                                new ResourceNotFoundException(
                                        "SIM not found"));

        if (simCard.getStatus() != SimStatus.PENDING_KYC) {

            throw new BusinessException(
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
                        new ResourceNotFoundException(
                                "User not found"));

        return simCardRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SimResponse mapToResponse(SimCard simCard) {

        Long userId =
                simCard.getUser() != null
                        ? simCard.getUser().getId()
                        : null;

        return new SimResponse(
                simCard.getId(),
                simCard.getPhoneNumber(),
                simCard.getImsiNumber(),
                simCard.getStatus(),
                userId
        );
    }
}