package com.nikolay.dto;

public class CreateEventLogDTO {
    private String email;
    private String name;
    private String move;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public CreateEventLogDTO(String email, String name, String move) {
        this.email = email;
        this.name = name;
        this.move = move;
    }
}
