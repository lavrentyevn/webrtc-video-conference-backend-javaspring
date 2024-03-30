package com.nikolay.services;

import com.nikolay.dto.UserLoginResponseDTO;
import com.nikolay.exceptions.ExpiredTokenException;
import com.nikolay.exceptions.InvalidTokenException;
import com.nikolay.exceptions.UserNotFoundException;
import com.nikolay.jwt.JwtService;
import com.nikolay.models.UserModel;
import com.nikolay.repositories.UserModelRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UserModelService implements LogoutHandler {

    private final UserModelRepository userModelRepository;
    private final JwtService jwtService;

    @Autowired
    public UserModelService(UserModelRepository userModelRepository, JwtService jwtService) {
        this.userModelRepository = userModelRepository;
        this.jwtService = jwtService;
    }

    public UserLoginResponseDTO refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        if (request.getCookies() == null) throw new InvalidTokenException();

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("jwt"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (refreshToken == null) throw new InvalidTokenException();

        String email = jwtService.extractEmail(refreshToken);
        String username = jwtService.extractUsername(refreshToken);

        // check if token has email

        if (email == null) throw new InvalidTokenException();

        // check if user exists

        UserModel userModel = userModelRepository.findByEmail(email).orElse(null);
        if (userModel == null) throw new UserNotFoundException();

        // check if refresh token has expired
        if (!jwtService.isTokenValid(refreshToken, userModel)) throw new ExpiredTokenException();

        // create new accessToken for client or guest

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        if (username != null) claims.put("username", username);

        String accessToken = jwtService.generateAccessToken(claims);

        UserLoginResponseDTO userLoginResponseDTO;
        if (username != null) {
            userLoginResponseDTO = new UserLoginResponseDTO(accessToken, email, username);
        } else {
            userLoginResponseDTO = new UserLoginResponseDTO(accessToken, email);
        }

        return userLoginResponseDTO;
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (request.getCookies() == null) throw new InvalidTokenException();

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("jwt"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        if (refreshToken == null) return;

        // check if user exists

        UserModel userModel = userModelRepository.findByRefreshToken(refreshToken).orElse(null);
        if (userModel != null) {
            userModel.setRefreshToken("");
            userModelRepository.save(userModel);
        }

        // clear cookie

        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
