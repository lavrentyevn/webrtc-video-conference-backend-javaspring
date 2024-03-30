package com.nikolay.dto;

public class CheckInvitationDTO {
    private String email;
    private String room;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public CheckInvitationDTO(String email, String room) {
        this.email = email;
        this.room = room;
    }
}
