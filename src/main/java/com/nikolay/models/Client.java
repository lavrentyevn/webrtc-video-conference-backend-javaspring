package com.nikolay.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    public Client(String username, String password, UserModel userModel) {
        this.username = username;
        this.password = password;
        this.userModel = userModel;
    }

    @Column(
            unique = true,
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String username;

    @Column(
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String password;

    @Column(
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE",
            name = "last_login"
    )
    private LocalDateTime lastLogin;

    @OneToOne(
            cascade = {CascadeType.REMOVE, CascadeType.REMOVE}
    )
    @JoinColumn(
            name = "user_model_id",
            referencedColumnName = "id"
    )
    private UserModel userModel;

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Client() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }
}
