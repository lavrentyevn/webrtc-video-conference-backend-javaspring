package com.nikolay.controllers;

import com.nikolay.dto.AccessRoomDTO;
import com.nikolay.dto.CheckRoomsDTO;
import com.nikolay.dto.CreateRoomDTO;
import com.nikolay.dto.RoomResponse;
import com.nikolay.exceptions.*;
import com.nikolay.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public void createRoom(@RequestBody CreateRoomDTO createRoomDTO) {
        roomService.createRoom(createRoomDTO);
    }

    @PostMapping("/access")
    public void accessRoom(@RequestBody AccessRoomDTO accessRoomDTO) { roomService.accessRoom(accessRoomDTO); }

    @PostMapping("/check")
    public List<RoomResponse> checkRooms(@RequestBody CheckRoomsDTO checkRoomsDTO) { return roomService.checkRooms(checkRoomsDTO); }




}
