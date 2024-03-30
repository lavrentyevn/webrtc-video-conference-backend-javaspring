package com.nikolay.services;

import com.nikolay.dto.UserLoginResponseDTO;
import com.nikolay.dto.CreateClientDTO;
import com.nikolay.dto.LoginClientDTO;
import com.nikolay.exceptions.*;
import com.nikolay.jwt.JwtService;
import com.nikolay.models.Client;
import com.nikolay.models.Guest;
import com.nikolay.models.UserModel;
import com.nikolay.repositories.ClientRepository;
import com.nikolay.repositories.GuestRepository;
import com.nikolay.repositories.UserModelRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserModelRepository userModelRepository;
    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final EmailVerificationService emailVerificationService;

    @Autowired
    public ClientService(ClientRepository clientRepository, UserModelRepository userModelRepository, GuestRepository guestRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, EmailService emailService, EmailVerificationService emailVerificationService) {
        this.clientRepository = clientRepository;
        this.userModelRepository = userModelRepository;
        this.guestRepository = guestRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.emailVerificationService = emailVerificationService;
    }

    @Transactional
    public void createClient(CreateClientDTO clientDTO) {
        // check if there is a guest with this email
        Guest guest = guestRepository.findByUserModelEmail(clientDTO.getEmail()).orElse(null);
        if (guest != null) {
            guestRepository.delete(guest);
            System.out.println("Found and deleted guest");
        }

        // check if username exists
        if (clientRepository.findByUsername(clientDTO.getUsername()).isPresent()) throw new UsernameExistsException();

        // check if email exists
        if (userModelRepository.findByEmail(clientDTO.getEmail()).isPresent()) throw new EmailExistsException();

        UserModel userModel = new UserModel(clientDTO.getEmail(), false, LocalDateTime.now());

        Client client = new Client(clientDTO.getUsername(), passwordEncoder.encode(clientDTO.getPassword()), userModel);

        userModel.setClient(client);

        // send email verification

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", client.getUserModel().getEmail());
        claims.put("username", client.getUsername());

        String verificationToken = jwtService.generateVerificationToken(claims);
        emailService.sendClientVerification(clientDTO.getEmail(), verificationToken);

        userModelRepository.save(userModel);

        emailVerificationService.createEmailVerification(clientDTO.getEmail());
    }

    @Transactional
    public UserLoginResponseDTO loginClient(LoginClientDTO loginClientDTO, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginClientDTO.getUsername(),
                loginClientDTO.getPassword()
        ));

        // find by username or email
        Client client = clientRepository.findByUsername(loginClientDTO.getUsername())
                .orElse(clientRepository.findByUserModelEmail(loginClientDTO.getUsername())
                        .orElse(null));
        if (client == null) throw new UserNotFoundException();


        // check if user is verified
        if (!client.getUserModel().isVerified()) throw new UserNotVerifiedException();

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", client.getUserModel().getEmail());
        claims.put("username", client.getUsername());

        String accessToken = jwtService.generateAccessToken(claims);
        String refreshToken = jwtService.generateRefreshToken(claims);

        client.getUserModel().setRefreshToken(refreshToken);
        clientRepository.save(client);

        UserLoginResponseDTO responseDTO = new UserLoginResponseDTO(accessToken, client.getUserModel().getEmail(), client.getUsername());

        // add cookie
        Cookie cookie = new Cookie("jwt", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(24 * 60 * 60 * 1000);
        cookie.setPath("/");
        response.addCookie(cookie);

        return responseDTO;
    }

    @Transactional
    public void verifyClient(String token) {
        // find user by decoded email
        UserModel userModel = userModelRepository.findByEmail(jwtService.extractEmail(token)).orElse(null);
        if (userModel == null) throw new UserNotFoundException();
        if (userModel.isVerified()) throw new UserAlreadyVerifiedException();

        userModel.setVerified(true);
        userModelRepository.save(userModel);
    }
}
