package com.provider.telecom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.provider.telecom.entity.User;
import com.provider.telecom.enums.Role;
import com.provider.telecom.repository.UserRepository;

@Configuration
public class DataSeeder {

    @Value("${app.seed.enabled:true}")
    private boolean seedEnabled;

    @Value("${app.seed.admin.email:admin@telecom.com}")
    private String adminEmail;

    @Value("${app.seed.admin.password:}")
    private String adminPassword;

    @Value("${app.seed.admin.name:System Administrator}")
    private String adminName;

    @Value("${app.seed.admin.phone:9999999999}")
    private String adminPhone;

    @Value("${app.seed.customer.email:demo@telecom.com}")
    private String customerEmail;

    @Value("${app.seed.customer.password:}")
    private String customerPassword;

    @Value("${app.seed.customer.name:Demo Customer}")
    private String customerName;

    @Value("${app.seed.customer.phone:8888888888}")
    private String customerPhone;


    @Bean
    public CommandLineRunner seedUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (!seedEnabled) {
                return;
            }

            /*
             * Admin
             */
            if (!userRepository.existsByEmail(adminEmail)) {

                User admin = new User();

                admin.setFullName(adminName);
                admin.setEmail(adminEmail);
                admin.setPassword(
                        passwordEncoder.encode(adminPassword)
                );
                admin.setPhone(adminPhone);
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);
            }


            /*
             * Demo Customer
             */
            if (!userRepository.existsByEmail(customerEmail)) {

                User customer = new User();

                customer.setFullName(customerName);
                customer.setEmail(customerEmail);
                customer.setPassword(
                        passwordEncoder.encode(customerPassword)
                );
                customer.setPhone(customerPhone);
                customer.setRole(Role.CUSTOMER);

                userRepository.save(customer);
            }
        };
    }
}