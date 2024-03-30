package com.nikolay.services;

import com.nikolay.dto.LogMessageDTO;
import com.nikolay.exceptions.RoomNotFoundException;
import com.nikolay.exceptions.UserNotFoundException;
import com.nikolay.models.Message;
import com.nikolay.models.Room;
import com.nikolay.models.UserModel;
import com.nikolay.repositories.MessageRepository;
import com.nikolay.repositories.RoomRepository;
import com.nikolay.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserModelRepository userModelRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, RoomRepository roomRepository, UserModelRepository userModelRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.userModelRepository = userModelRepository;
    }


    @Transactional
    public void logMessage(LogMessageDTO logMessageDTO) {
        // no room found
        Room room = roomRepository.findByName(logMessageDTO.getRoomname()).orElse(null);
        if (room == null) throw new RoomNotFoundException();

        // no user found
        UserModel userModel = userModelRepository.findByEmail(logMessageDTO.getEmail()).orElse(null);
        if (userModel == null) throw new UserNotFoundException();

        Message message = new Message(logMessageDTO.getMessage(), LocalDateTime.now());
        userModel.addMessage(message);
        room.addMessage(message);

        messageRepository.save(message);
    }
}
