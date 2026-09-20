package com.provider.telecom.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationCode(
            String recipient,
            String code
    ) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(recipient);
        message.setSubject("Telecom - Email Verification Code");

        message.setText(
                """
                Hello,
                Your Telecom account verification code is: """
                + code
                + "\n\n"
                + "This code will expire in 10 minutes.\n"
                + "Do not share this code with anyone.\n\n"
                + "Regards,\n"
                + "Telecom Service Provider"
        );

        mailSender.send(message);
    }
}