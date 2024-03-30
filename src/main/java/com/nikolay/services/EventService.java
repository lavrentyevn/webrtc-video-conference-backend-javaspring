package com.nikolay.services;

import com.nikolay.dto.CreateEventDTO;
import com.nikolay.exceptions.RoomNotFoundException;
import com.nikolay.models.Event;
import com.nikolay.models.Room;
import com.nikolay.repositories.EventRepository;
import com.nikolay.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public EventService(EventRepository eventRepository, RoomRepository roomRepository) {
        this.eventRepository = eventRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public void createEvent(CreateEventDTO createEventDTO) {
        // no room found
        Room room = roomRepository.findByName(createEventDTO.getRoom()).orElse(null);
        if (room == null) throw new RoomNotFoundException();


        Event event = new Event(room);
        room.setEvent(event);

        eventRepository.save(event);
    }
}
