package com.provider.telecom.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.provider.telecom.dto.usage.UsageRequest;
import com.provider.telecom.dto.usage.UsageResponse;
import com.provider.telecom.entity.SimCard;
import com.provider.telecom.entity.Subscription;
import com.provider.telecom.entity.User;
import com.provider.telecom.enums.SimStatus;
import com.provider.telecom.enums.SubscriptionStatus;
import com.provider.telecom.enums.UsageType;
import com.provider.telecom.exception.BusinessException;
import com.provider.telecom.exception.ResourceAccessDeniedException;
import com.provider.telecom.exception.ResourceNotFoundException;
import com.provider.telecom.repository.SimCardRepository;
import com.provider.telecom.repository.SubscriptionRepository;
import com.provider.telecom.repository.UserRepository;

@Service
public class UsageService {

    private final UserRepository userRepository;
    private final SimCardRepository simCardRepository;
    private final SubscriptionRepository subscriptionRepository;

    public UsageService(
            UserRepository userRepository,
            SimCardRepository simCardRepository,
            SubscriptionRepository subscriptionRepository) {

        this.userRepository = userRepository;
        this.simCardRepository = simCardRepository;
        this.subscriptionRepository =
                subscriptionRepository;
    }

    @Transactional
    public UsageResponse simulateUsage(
            String email,
            UsageRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        SimCard simCard =
                simCardRepository.findById(
                        request.getSimCardId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException("SIM not found"));

        if (!simCard.getUser().getId().equals(user.getId())) {
            throw new ResourceAccessDeniedException(
                    "You are not authorized to use this SIM");
        }

        if (simCard.getStatus() != SimStatus.ACTIVE) {
            throw new BusinessException(
                    "SIM must be ACTIVE");
        }

        Subscription subscription =
                subscriptionRepository
                        .findBySimCardIdAndStatus(
                                simCard.getId(),
                                SubscriptionStatus.ACTIVE
                        )
                        .orElseThrow(() ->
                                new BusinessException(
                                        "No active subscription found"));

        if (subscription.getExpiryDate()
                .isBefore(LocalDate.now())) {

            subscription.setStatus(
                    SubscriptionStatus.EXPIRED
            );

            subscriptionRepository.save(subscription);

            throw new BusinessException(
                    "Subscription has expired");
        }

        if (request.getUsageType() == UsageType.DATA) {

            double remainingData =
                    subscription.getRemainingDataMb();

            if (request.getAmount() > remainingData) {
                throw new BusinessException(
                        "Insufficient data balance");
            }

            subscription.setRemainingDataMb(
                    remainingData - request.getAmount()
            );
        }

        if (request.getUsageType() == UsageType.CALL) {

            int requestedMinutes =
                    request.getAmount().intValue();

            int remainingMinutes =
                    subscription
                            .getRemainingTalktimeMins();

            if (requestedMinutes > remainingMinutes) {
                throw new BusinessException(
                        "Insufficient talktime balance");
            }

            subscription.setRemainingTalktimeMins(
                    remainingMinutes - requestedMinutes
            );
        }

        Subscription savedSubscription =
                subscriptionRepository.save(subscription);

        return new UsageResponse(
                savedSubscription.getId(),
                request.getUsageType(),
                request.getAmount(),
                savedSubscription.getRemainingDataMb(),
                savedSubscription
                        .getRemainingTalktimeMins()
        );
    }
}