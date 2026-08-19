package com.provider.telecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.provider.telecom.entity.Transaction;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserIdOrderByTimestampDesc(Long userId);
}