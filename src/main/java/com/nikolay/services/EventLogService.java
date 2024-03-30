package com.nikolay.services;

import com.nikolay.dto.CreateEventLogDTO;
import com.nikolay.exceptions.EventNotFoundException;
import com.nikolay.exceptions.UserNotFoundException;
import com.nikolay.models.Event;
import com.nikolay.models.EventLog;
import com.nikolay.models.UserModel;
import com.nikolay.repositories.EventLogRepository;
import com.nikolay.repositories.EventRepository;
import com.nikolay.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class EventLogService {

    private final EventLogRepository eventLogRepository;
    private final UserModelRepository userModelRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventLogService(EventLogRepository eventLogRepository, UserModelRepository userModelRepository, EventRepository eventRepository) {
        this.eventLogRepository = eventLogRepository;
        this.userModelRepository = userModelRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public void createEventLog(CreateEventLogDTO createEventLogDTO) {
        // no user found
        UserModel userModel = userModelRepository.findByEmail(createEventLogDTO.getEmail()).orElse(null);
        if (userModel == null) throw new UserNotFoundException();

        // no event found
        Event event = eventRepository.findByRoomName(createEventLogDTO.getName()).orElse(null);
        if (event == null) throw new EventNotFoundException();

        System.out.println(createEventLogDTO.getMove());
        // check if the user joined (j) or left (l) the room
        EventLog eventLog;
        if (createEventLogDTO.getMove().equals("j")) {
            eventLog = new EventLog(LocalDateTime.now());
            userModel.addEventLog(eventLog);
            event.addEventLog(eventLog);

        } else if (createEventLogDTO.getMove().equals("l")) {
            eventLog = eventLogRepository.findFirstByEventIdAndUserModelEmailOrderByIdDesc(event.getId(), createEventLogDTO.getEmail()).orElse(null);
            eventLog.setLeftAt(LocalDateTime.now());

        } else return;

        eventLogRepository.save(eventLog);
    }
}
