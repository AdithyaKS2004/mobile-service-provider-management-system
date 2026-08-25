package com.provider.telecom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.provider.telecom.entity.SimCard;
import com.provider.telecom.enums.SimStatus;

import jakarta.persistence.LockModeType;

public interface SimCardRepository extends JpaRepository<SimCard, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByImsiNumber(String imsiNumber);

    List<SimCard> findByStatus(SimStatus status);

    List<SimCard> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM SimCard s WHERE s.id = :id")
    Optional<SimCard> findByIdWithLock(@Param("id") Long id);
}