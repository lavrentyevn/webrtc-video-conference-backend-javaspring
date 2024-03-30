package com.nikolay.services;

import com.nikolay.dto.AccessRoomDTO;
import com.nikolay.dto.CheckRoomsDTO;
import com.nikolay.dto.CreateRoomDTO;
import com.nikolay.dto.RoomResponse;
import com.nikolay.exceptions.RoomNotFoundException;
import com.nikolay.exceptions.UserNotFoundException;
import com.nikolay.exceptions.RoomExistsException;
import com.nikolay.exceptions.WrongPasswordException;
import com.nikolay.models.Client;
import com.nikolay.models.Room;
import com.nikolay.models.UserModel;
import com.nikolay.repositories.ClientRepository;
import com.nikolay.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RoomService(RoomRepository roomRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.roomRepository = roomRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createRoom(CreateRoomDTO createRoomDTO) {
        // room name is taken
        if (roomRepository.findByName(createRoomDTO.getName()).isPresent()) throw new RoomExistsException();

        Client client = clientRepository.findByUsername(createRoomDTO.getCreator()).orElse(null);

        // user not found
        if (client == null) throw new UserNotFoundException();

        Room room = new Room(createRoomDTO.getName(), passwordEncoder.encode(createRoomDTO.getPassword()), createRoomDTO.getDescription(), LocalDateTime.now());

        UserModel userModel = client.getUserModel();
        userModel.addRoom(room);

        roomRepository.save(room);

    }


    public void accessRoom(AccessRoomDTO accessRoomDTO) {
        // no room found
        Room room = roomRepository.findByName(accessRoomDTO.getName()).orElse(null);
        if (room == null) throw new RoomNotFoundException();

        // wrong password
        if (!passwordEncoder.matches(accessRoomDTO.getPassword(), room.getPassword())) throw new WrongPasswordException();
    }

    public List<RoomResponse> checkRooms(CheckRoomsDTO checkRoomsDTO) {
        // in case there is no user yet, but we have to check what rooms are available
        Integer id = 0;

        Client client = clientRepository.findByUsername(checkRoomsDTO.getUsername()).orElse(null);
        if (client != null) id = client.getUserModel().getId();

        return roomRepository.getRooms(id);
    }
}
