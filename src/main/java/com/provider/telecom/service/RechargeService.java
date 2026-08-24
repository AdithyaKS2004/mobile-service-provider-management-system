package com.provider.telecom.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.provider.telecom.dto.subscription.SubscriptionResponse;
import com.provider.telecom.entity.Plan;
import com.provider.telecom.entity.SimCard;
import com.provider.telecom.entity.Subscription;
import com.provider.telecom.entity.Transaction;
import com.provider.telecom.entity.User;
import com.provider.telecom.enums.PaymentStatus;
import com.provider.telecom.enums.SimStatus;
import com.provider.telecom.enums.SubscriptionStatus;
import com.provider.telecom.exception.BusinessException;
import com.provider.telecom.exception.ResourceAccessDeniedException;
import com.provider.telecom.exception.ResourceNotFoundException;
import com.provider.telecom.repository.PlanRepository;
import com.provider.telecom.repository.SimCardRepository;
import com.provider.telecom.repository.SubscriptionRepository;
import com.provider.telecom.repository.TransactionRepository;
import com.provider.telecom.repository.UserRepository;

@Service
public class RechargeService {

    private final UserRepository userRepository;
    private final SimCardRepository simCardRepository;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TransactionRepository transactionRepository;

    public RechargeService(
            UserRepository userRepository,
            SimCardRepository simCardRepository,
            PlanRepository planRepository,
            SubscriptionRepository subscriptionRepository,
            TransactionRepository transactionRepository) {

        this.userRepository = userRepository;
        this.simCardRepository = simCardRepository;
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public SubscriptionResponse recharge(
            String email,
            Long simCardId,
            Long planId,
            String paymentMethod) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        SimCard simCard = simCardRepository.findById(simCardId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("SIM not found"));

        if (simCard.getUser() == null || !simCard.getUser().getId().equals(user.getId())) {
            throw new ResourceAccessDeniedException(
                    "You are not authorized to recharge this SIM");
        }

        if (simCard.getStatus() != SimStatus.ACTIVE) {
            throw new BusinessException(
                    "SIM must be ACTIVE before recharge");
        }

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Plan not found"));

        if (!plan.isActive()) {
            throw new BusinessException(
                    "Selected plan is inactive");
        }

        subscriptionRepository
                .findBySimCardIdAndStatus(
                        simCardId,
                        SubscriptionStatus.ACTIVE
                )
                .ifPresent(existingSubscription -> {

                    existingSubscription.setStatus(
                            SubscriptionStatus.EXPIRED
                    );

                    subscriptionRepository.save(
                            existingSubscription
                    );
                });

        LocalDate startDate = LocalDate.now();

        LocalDate expiryDate =
                startDate.plusDays(plan.getValidityDays());

        double remainingDataMb =
                plan.getDataLimitGbPerDay() * 1024;

        Subscription subscription = new Subscription();

        subscription.setSimCard(simCard);
        subscription.setPlan(plan);
        subscription.setStartDate(startDate);
        subscription.setExpiryDate(expiryDate);
        subscription.setRemainingDataMb(remainingDataMb);
        subscription.setRemainingTalktimeMins(
                plan.getTalktimeMins()
        );
        subscription.setStatus(
                SubscriptionStatus.ACTIVE
        );

        Subscription savedSubscription =
                subscriptionRepository.save(subscription);

        Transaction transaction = new Transaction();

        transaction.setUser(user);
        transaction.setPlan(plan);
        transaction.setAmount(plan.getPrice());
        transaction.setPaymentStatus(
                PaymentStatus.SUCCESS
        );
        transaction.setTimestamp(
                java.time.LocalDateTime.now()
        );
        transaction.setPaymentMethod(paymentMethod);

        transactionRepository.save(transaction);

        return mapToResponse(savedSubscription);
    }

    private SubscriptionResponse mapToResponse(
            Subscription subscription) {

        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getSimCard().getId(),
                subscription.getPlan().getId(),
                subscription.getPlan().getName(),
                subscription.getStartDate(),
                subscription.getExpiryDate(),
                subscription.getRemainingDataMb(),
                subscription.getRemainingTalktimeMins(),
                subscription.getStatus()
        );
    }

    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getUserSubscriptions(
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return subscriptionRepository
                .findBySimCardUserIdAndStatus(
                        user.getId(),
                        SubscriptionStatus.ACTIVE
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}