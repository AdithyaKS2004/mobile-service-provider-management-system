package com.provider.telecom.repository;

import com.provider.telecom.enums.SimStatus;
import com.provider.telecom.entity.SimCard;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface SimCardRepository extends JpaRepository<SimCard, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByImsiNumber(String imsiNumber);

    List<SimCard> findByStatus(SimStatus status);

    List<SimCard> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SimCard> findByIdWithLock(Long id);
}