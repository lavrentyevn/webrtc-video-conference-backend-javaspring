package com.nikolay.controllers;

import com.nikolay.dto.CheckInvitationDTO;
import com.nikolay.dto.CreateInvitationDTO;
import com.nikolay.exceptions.ExceptionResponse;
import com.nikolay.exceptions.RoomNotFoundException;
import com.nikolay.exceptions.UserNotFoundException;
import com.nikolay.services.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/invitation")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class InvitationController {

    private final InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    public void createInvitation(@RequestBody CreateInvitationDTO createInvitationDTO) {
        invitationService.createInvitation(createInvitationDTO);
    }

    @PostMapping("/check")
    public void checkInvitation(@RequestBody CheckInvitationDTO checkInvitationDTO) {
        invitationService.checkInvitation(checkInvitationDTO);
    }
}
