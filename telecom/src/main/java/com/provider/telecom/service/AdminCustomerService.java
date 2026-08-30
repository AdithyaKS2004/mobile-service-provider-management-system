package com.provider.telecom.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.provider.telecom.dto.customer.CustomerResponse;
import com.provider.telecom.enums.Role;
import com.provider.telecom.repository.UserRepository;

@Service
public class AdminCustomerService {

    private final UserRepository userRepository;

    public AdminCustomerService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {

        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.CUSTOMER)
                .map(user -> new CustomerResponse(
                        user.getId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getRole()
                ))
                .toList();
    }
}