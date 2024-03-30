package com.nikolay.dto;

public class CreateRoomDTO {
    private String name;
    private String password;
    private String description;
    private String creator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public CreateRoomDTO(String name, String password, String description, String creator) {
        this.name = name;
        this.password = password;
        this.description = description;
        this.creator = creator;
    }
}
