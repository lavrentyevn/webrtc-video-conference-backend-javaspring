package com.nikolay.controllers;

import com.nikolay.dto.CreateEventLogDTO;
import com.nikolay.services.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class EventLogController {

    private final EventLogService eventLogService;

    @Autowired
    public EventLogController(EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @PostMapping("/log")
    public void createEventLog(@RequestBody CreateEventLogDTO createEventLogDTO) {
        eventLogService.createEventLog(createEventLogDTO);
    }
}
