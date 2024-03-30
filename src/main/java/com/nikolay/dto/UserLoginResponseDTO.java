package com.nikolay.dto;

public class UserLoginResponseDTO {
    private String accessToken;
    private String email;
    private String username;

    public UserLoginResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserLoginResponseDTO(String accessToken, String email) {
        this.accessToken = accessToken;
        this.email = email;
    }

    public UserLoginResponseDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserLoginResponseDTO(String accessToken, String email, String username) {
        this.accessToken = accessToken;
        this.email = email;
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


}
