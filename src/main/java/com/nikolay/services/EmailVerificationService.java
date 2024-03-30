package com.nikolay.services;

import com.nikolay.exceptions.UserNotFoundException;
import com.nikolay.models.EmailVerification;
import com.nikolay.models.UserModel;
import com.nikolay.repositories.EmailVerificationRepository;
import com.nikolay.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final UserModelRepository userModelRepository;

    @Autowired
    public EmailVerificationService(EmailVerificationRepository emailVerificationRepository, UserModelRepository userModelRepository) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.userModelRepository = userModelRepository;
    }

    @Transactional
    public void createEmailVerification(String email) {
        // check if user exists
        UserModel userModel = userModelRepository.findByEmail(email).orElse(null);
        if (userModel == null) throw new UserNotFoundException();

        EmailVerification emailVerification = new EmailVerification(LocalDateTime.now());
        userModel.addEmailVerification(emailVerification);

        emailVerificationRepository.save(emailVerification);
    }
}
