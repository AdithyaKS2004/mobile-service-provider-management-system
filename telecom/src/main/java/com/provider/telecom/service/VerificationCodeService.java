package com.provider.telecom.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.provider.telecom.entity.User;
import com.provider.telecom.entity.VerificationCode;
import com.provider.telecom.enums.VerificationChannel;
import com.provider.telecom.enums.VerificationPurpose;
import com.provider.telecom.exception.ResourceNotFoundException;
import com.provider.telecom.repository.UserRepository;
import com.provider.telecom.repository.VerificationCodeRepository;

@Service
public class VerificationCodeService {

    private static final int OTP_EXPIRY_MINUTES = 10;
    private static final int MAX_ATTEMPTS = 5;

    private final VerificationCodeRepository verificationCodeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();

    public VerificationCodeService(
            VerificationCodeRepository verificationCodeRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {

        this.verificationCodeRepository = verificationCodeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public void sendEmailVerificationCode(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No account found with this email"
                        )
                );

        if (user.isEmailVerified()) {
            throw new IllegalStateException(
                    "Email is already verified"
            );
        }

        invalidatePreviousCodes(
                user.getId(),
                VerificationPurpose.EMAIL_VERIFICATION,
                VerificationChannel.EMAIL
        );

        String code = generateOtp();

        VerificationCode verificationCode =
                new VerificationCode();

        verificationCode.setUser(user);
        verificationCode.setCodeHash(
                passwordEncoder.encode(code)
        );
        verificationCode.setPurpose(
                VerificationPurpose.EMAIL_VERIFICATION
        );
        verificationCode.setChannel(
                VerificationChannel.EMAIL
        );
        verificationCode.setExpiresAt(
                LocalDateTime.now()
                        .plusMinutes(OTP_EXPIRY_MINUTES)
        );
        verificationCode.setUsed(false);
        verificationCode.setAttempts(0);
        verificationCode.setCreatedAt(LocalDateTime.now());

        verificationCodeRepository.save(verificationCode);

        emailService.sendVerificationCode(
                user.getEmail(),
                code
        );
    }

    @Transactional(
        noRollbackFor = {
                IllegalArgumentException.class,
                IllegalStateException.class
        }
    )
    public void verifyEmail(
            String email,
            String code
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No account found with this email"
                        )
                );

        if (user.isEmailVerified()) {
            throw new IllegalStateException(
                    "Email is already verified"
            );
        }

        VerificationCode verificationCode =
                verificationCodeRepository
                        .findTopByUserIdAndPurposeAndChannelAndUsedFalseOrderByCreatedAtDesc(
                                user.getId(),
                                VerificationPurpose.EMAIL_VERIFICATION,
                                VerificationChannel.EMAIL
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "No active verification code found"
                                )
                        );

        if (verificationCode.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            verificationCode.setUsed(true);
            verificationCodeRepository.save(
                    verificationCode
            );

            throw new IllegalStateException(
                    "Verification code has expired"
            );
        }

        if (verificationCode.getAttempts() >= MAX_ATTEMPTS) {

            verificationCode.setUsed(true);
            verificationCodeRepository.save(
                    verificationCode
            );

            throw new IllegalStateException(
                    "Maximum verification attempts exceeded"
            );
        }

        if (!passwordEncoder.matches(
                code,
                verificationCode.getCodeHash())) {
        
            int attempts = verificationCode.getAttempts() + 1;
        
            verificationCode.setAttempts(attempts);
        
            if (attempts >= MAX_ATTEMPTS) {
                verificationCode.setUsed(true);
            }
        
            verificationCodeRepository.save(verificationCode);
        
            if (attempts >= MAX_ATTEMPTS) {
                throw new IllegalStateException(
                        "Maximum verification attempts exceeded"
                );
            }
        
            throw new IllegalArgumentException(
                    "Invalid verification code"
            );
        }

        verificationCode.setUsed(true);

        user.setEmailVerified(true);
        user.setEnabled(true);

        verificationCodeRepository.save(
                verificationCode
        );

        userRepository.save(user);
    }

    private void invalidatePreviousCodes(
            Long userId,
            VerificationPurpose purpose,
            VerificationChannel channel
    ) {

        List<VerificationCode> activeCodes =
                verificationCodeRepository
                        .findByUserIdAndPurposeAndChannelAndUsedFalse(
                                userId,
                                purpose,
                                channel
                        );

        for (VerificationCode code : activeCodes) {
            code.setUsed(true);
        }

        if (!activeCodes.isEmpty()) {
            verificationCodeRepository.saveAll(activeCodes);
        }
    }

    private String generateOtp() {

        int number = secureRandom.nextInt(1_000_000);

        return String.format("%06d", number);
    }
}