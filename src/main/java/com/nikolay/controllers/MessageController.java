package com.nikolay.controllers;

import com.nikolay.dto.LogMessageDTO;
import com.nikolay.exceptions.ExceptionResponse;
import com.nikolay.exceptions.RoomExistsException;
import com.nikolay.exceptions.RoomNotFoundException;
import com.nikolay.exceptions.UserNotFoundException;
import com.nikolay.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public void logMessage(@RequestBody LogMessageDTO logMessageDTO) {
        messageService.logMessage(logMessageDTO);
    }


}
