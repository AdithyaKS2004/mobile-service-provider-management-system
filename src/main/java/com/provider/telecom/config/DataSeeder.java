package com.provider.telecom.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.provider.telecom.entity.User;
import com.provider.telecom.enums.Role;
import com.provider.telecom.repository.UserRepository;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (!userRepository.existsByEmail("admin@telecom.com")) {

                User admin = new User();

                admin.setFullName("System Administrator");
                admin.setEmail("admin@telecom.com");
                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );
                admin.setPhone("9999999999");
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);
            }

            if (!userRepository.existsByEmail("demo@telecom.com")) {

                User customer = new User();

                customer.setFullName("Demo Customer");
                customer.setEmail("demo@telecom.com");
                customer.setPassword(
                        passwordEncoder.encode("customer123")
                );
                customer.setPhone("8888888888");
                customer.setRole(Role.CUSTOMER);

                userRepository.save(customer);
            }
        };
    }
}