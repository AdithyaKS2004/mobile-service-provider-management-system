package com.provider.telecom.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.provider.telecom.dto.auth.LoginRequest;
import com.provider.telecom.dto.auth.RegisterRequest;
import com.provider.telecom.dto.auth.RegisterResponse;
import com.provider.telecom.entity.User;
import com.provider.telecom.enums.Role;
import com.provider.telecom.exception.ResourceAlreadyExistsException;
import com.provider.telecom.repository.UserRepository;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeService verificationCodeService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            VerificationCodeService verificationCodeService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.verificationCodeService = verificationCodeService;
    }

    public User authenticateUser(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
    
        return userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );
    }

    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );
    }

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "An account with this email already exists"
            );
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new ResourceAlreadyExistsException(
                    "An account with this phone number already exists"
            );
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Public registration can only create customers.
        user.setRole(Role.CUSTOMER);
        
        user.setEmailVerified(false);
        user.setPhoneVerified(false);
        user.setEnabled(false);

        User savedUser = userRepository.save(user);

        verificationCodeService.sendEmailVerificationCode(
                savedUser.getEmail()
        );

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getRole(),
                "Regristration successful. Please verify your email."
        );
    }
}