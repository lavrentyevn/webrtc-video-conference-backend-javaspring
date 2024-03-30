package com.nikolay.controllers;

import com.nikolay.dto.UserLoginResponseDTO;
import com.nikolay.dto.CreateGuestDTO;
import com.nikolay.services.GuestService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guest")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    public void createGuest(@RequestBody CreateGuestDTO createGuestDTO) {
        guestService.createGuest(createGuestDTO);
    }

    @PutMapping("/verify")
    public UserLoginResponseDTO loginGuest(@RequestParam String token, HttpServletResponse response) {
       return guestService.loginGuest(token, response);
    }
}
