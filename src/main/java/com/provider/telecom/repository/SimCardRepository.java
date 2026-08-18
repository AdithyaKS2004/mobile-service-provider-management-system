package com.provider.telecom.repository;

import com.provider.telecom.entity.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimCardRepository extends JpaRepository<SimCard, Long> {

    List<SimCard> findByUserId(Long userId);
}