package com.provider.telecom.repository;

import com.provider.telecom.enums.SimStatus;
import com.provider.telecom.entity.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimCardRepository extends JpaRepository<SimCard, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByImsiNumber(String imsiNumber);

    List<SimCard> findByStatus(SimStatus status);

    List<SimCard> findByUserId(Long userId);
}