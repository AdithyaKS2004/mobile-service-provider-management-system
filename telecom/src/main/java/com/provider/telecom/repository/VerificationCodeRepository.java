package com.provider.telecom.repository;

import com.provider.telecom.entity.VerificationCode;
import com.provider.telecom.enums.VerificationChannel;
import com.provider.telecom.enums.VerificationPurpose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationCodeRepository
        extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode>
    findTopByUserIdAndPurposeAndChannelAndUsedFalseOrderByCreatedAtDesc(
            Long userId,
            VerificationPurpose purpose,
            VerificationChannel channel
    );

    List<VerificationCode>
    findByUserIdAndPurposeAndChannelAndUsedFalse(
            Long userId,
            VerificationPurpose purpose,
            VerificationChannel channel
    );
}