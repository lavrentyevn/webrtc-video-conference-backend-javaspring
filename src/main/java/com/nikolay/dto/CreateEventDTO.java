package com.nikolay.dto;

public class CreateEventDTO {
    private String room;

    public CreateEventDTO() {
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public CreateEventDTO(String room) {
        this.room = room;
    }
}
