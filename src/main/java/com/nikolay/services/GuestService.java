package com.nikolay.services;

import com.nikolay.dto.UserLoginResponseDTO;
import com.nikolay.dto.CreateGuestDTO;
import com.nikolay.exceptions.EmailExistsException;
import com.nikolay.exceptions.UserAlreadyVerifiedException;
import com.nikolay.exceptions.UserNotFoundException;
import com.nikolay.jwt.JwtService;
import com.nikolay.models.Guest;
import com.nikolay.models.UserModel;
import com.nikolay.repositories.GuestRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class GuestService {

    private final GuestRepository guestRepository;
    private final EmailVerificationService emailVerificationService;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public GuestService(GuestRepository guestRepository, EmailVerificationService emailVerificationService, JwtService jwtService, EmailService emailService, AuthenticationManager authenticationManager) {
        this.guestRepository = guestRepository;
        this.emailVerificationService = emailVerificationService;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void createGuest(CreateGuestDTO createGuestDTO) throws MessagingException {
        // check if already exists
        Guest guest = guestRepository.findByUserModelEmail(createGuestDTO.getEmail()).orElse(null);
        if (guest != null) throw new EmailExistsException();

        UserModel userModel = new UserModel(createGuestDTO.getEmail(), false, LocalDateTime.now());
        guest = new Guest(userModel);

        userModel.setGuest(guest);

        guestRepository.save(guest);

        // send email verification

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", createGuestDTO.getEmail());

        String verificationToken = jwtService.generateVerificationToken(claims);
        emailService.sendGuestVerification(createGuestDTO.getEmail(), verificationToken);

        emailVerificationService.createEmailVerification(createGuestDTO.getEmail());
    }

    @Transactional
    public UserLoginResponseDTO loginGuest(String token, HttpServletResponse response) {

        // check if guest exists
        Guest guest = guestRepository.findByUserModelEmail(jwtService.extractEmail(token)).orElse(null);
        if (guest == null) throw new UserNotFoundException();
        if (guest.getUserModel().isVerified()) throw new UserAlreadyVerifiedException();

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", guest.getUserModel().getEmail());

        String accessToken = jwtService.generateAccessToken(claims);
        String refreshToken = jwtService.generateRefreshToken(claims);

        guest.getUserModel().setRefreshToken(refreshToken);
        guestRepository.save(guest);

        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO(accessToken);

        // add cookie
        Cookie cookie = new Cookie("jwt", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(24 * 60 * 60 * 1000);
        cookie.setPath("/");
        response.addCookie(cookie);

        return responseDTO;
    }
}
