package com.nikolay.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @Column(
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String name;

    @Column(
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String password;

    @Column(
            columnDefinition = "VARCHAR"
    )
    private String description;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinColumn(
            name = "creator_id",
            referencedColumnName = "id"
    )
    private UserModel userModel;

    @Column(
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE",
            name = "created_at"
    )
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "room",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Invitation> invitation = new ArrayList<>();

    @OneToMany(
            mappedBy = "room",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Message> message = new ArrayList<>();

    @OneToOne(
            mappedBy = "room",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Event event;

    public Room(String name, String password, String description, LocalDateTime createdAt) {
        this.name = name;
        this.password = password;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Room() {
    }

    public void addMessage(Message message) {
        if (!this.message.contains(message)) {
            this.message.add(message);
            message.setRoom(this);
        }
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void addInvitation(Invitation invitation) {
        if (!this.invitation.contains(invitation)) {
            this.invitation.add(invitation);
            invitation.setRoom(this);
        }
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

