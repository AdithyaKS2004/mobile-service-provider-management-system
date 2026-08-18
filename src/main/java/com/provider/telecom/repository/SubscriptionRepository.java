package com.provider.telecom.repository;

import com.provider.telecom.entity.Subscription;
import com.provider.telecom.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository
        extends JpaRepository<Subscription, Long> {

    List<Subscription> findBySimCardIdAndStatus(
            Long simCardId,
            SubscriptionStatus status
    );
}