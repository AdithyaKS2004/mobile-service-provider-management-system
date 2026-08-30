package com.provider.telecom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provider.telecom.entity.Subscription;
import com.provider.telecom.enums.SubscriptionStatus;

public interface SubscriptionRepository
        extends JpaRepository<Subscription, Long> {
    
    List<Subscription> findBySimCardUserId(Long userId);

    List<Subscription> findBySimCardUserIdAndStatus(
            Long userId,
            SubscriptionStatus status
    );
    
    /*List<Subscription> findBySimCardIdAndStatus(
            Long simCardId,
            SubscriptionStatus status
    );*/

    Optional<Subscription> findBySimCardIdAndStatus(
            Long simCardId,
            SubscriptionStatus status
);
}