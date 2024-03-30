package com.nikolay.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;
    private String subject = "Account Verification";


    @Autowired
    private JavaMailSender emailSender;

    public void sendClientVerification(
            String to, String token) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText("<h1>Thanks for creating an account!</h1>\n" +
                "    <p>Here you can verify it and login into your account.</p>\n" +
                "    <a href=\"http://localhost:3000/verifyclient/" + token + "\">Log in</a>", true);
        emailSender.send(message);
    }

    public void sendGuestVerification(
            String to, String token) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText("<h1>Thanks for creating an account!</h1>\n" +
                "    <p>Here you can verify it and login into your account.</p>\n" +
                "    <a href=\"http://localhost:3000/verifyguest/" + token + "\">Log in</a>", true);
        emailSender.send(message);
    }
}
