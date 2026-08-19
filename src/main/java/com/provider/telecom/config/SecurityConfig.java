package com.provider.telecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            SecurityContextRepository securityContextRepository)
            throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                // Public endpoints
                .requestMatchers(
                        "/h2-console/**",
                        "/api/auth/register",
                        "/api/auth/login",
                        "/api/plans"
                ).permitAll()

                // Admin endpoints
                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                // Customer endpoints
                .requestMatchers(
                        "/api/customer/**",
                        "/api/user/**",
                        "/api/recharge",
                        "/api/sim/**",
                        "/api/user/subscriptions/**"
                ).hasRole("CUSTOMER")

                // Everything else requires authentication
                .anyRequest()
                .authenticated()
            )

            .csrf(csrf -> csrf.disable())

            .securityContext(context -> context
                    .securityContextRepository(
                            securityContextRepository
                    )
            )

            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessHandler(
                    (request, response, authentication) -> {
                        response.setStatus(
                            HttpServletResponse.SC_OK
                        );
                    }
                )
            )
            
            .headers(headers -> headers
                    .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }
}