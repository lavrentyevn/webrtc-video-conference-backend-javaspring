package com.nikolay.dto;

public class LogMessageDTO {
    private String roomname;
    private String email;
    private String message;

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogMessageDTO(String roomname, String email, String message) {
        this.roomname = roomname;
        this.email = email;
        this.message = message;
    }
}
