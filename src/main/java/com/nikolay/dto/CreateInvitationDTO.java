package com.nikolay.dto;

import java.util.List;

public class CreateInvitationDTO {
    private List<String> email;
    private String room;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public CreateInvitationDTO(List<String> email, String room) {
        this.email = email;
        this.room = room;
    }
}
