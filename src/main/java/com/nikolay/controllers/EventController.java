package com.nikolay.controllers;

import com.nikolay.dto.CreateEventDTO;
import com.nikolay.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public void createEvent(@RequestBody CreateEventDTO createEventDTO) {
        eventService.createEvent(createEventDTO);
    }
}
