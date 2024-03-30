package com.nikolay.dto;

public class CreateGuestDTO {
    private String email;

    public CreateGuestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CreateGuestDTO(String email) {
        this.email = email;
    }
}
