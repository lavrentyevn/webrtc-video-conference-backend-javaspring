package com.nikolay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;
    private String subject = "Account Verification";


    @Autowired
    private JavaMailSender emailSender;

    public void sendClientVerification(
            String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText("http://localhost:3000/verifyclient/" + token);
        emailSender.send(message);
    }

    public void sendGuestVerification(
            String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText("http://localhost:3000/verifyguest/" + token);
        emailSender.send(message);
    }
}
