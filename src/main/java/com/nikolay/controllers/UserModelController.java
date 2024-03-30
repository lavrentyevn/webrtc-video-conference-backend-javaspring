package com.nikolay.controllers;

import com.nikolay.dto.UserLoginResponseDTO;
import com.nikolay.services.UserModelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserModelController {

    private final UserModelService userModelService;

    @Autowired
    public UserModelController(UserModelService userModelService) {
        this.userModelService = userModelService;
    }

    @GetMapping("/refresh")
    public UserLoginResponseDTO refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException { return userModelService.refreshToken(request, response); }


    @PutMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        userModelService.logout(request, response, authentication);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
