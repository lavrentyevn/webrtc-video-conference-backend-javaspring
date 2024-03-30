package com.nikolay.dto;

public class CheckRoomsDTO {
    private String username;

    public CheckRoomsDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CheckRoomsDTO(String username) {
        this.username = username;
    }
}
