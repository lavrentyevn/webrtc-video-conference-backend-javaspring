package com.nikolay.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinColumn(
            name = "room_id",
            referencedColumnName = "id"
    )
    private Room room;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinColumn(
            name = "user_model_id",
            referencedColumnName = "id"
    )
    private UserModel userModel;

    @Column(
            nullable = false,
            name = "message_text"
    )
    private String messageText;

    @Column(
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE",
            name = "sent_at"
    )
    private LocalDateTime sentAt;

    public Message(String messageText, LocalDateTime sentAt) {
        this.messageText = messageText;
        this.sentAt = sentAt;
    }

    public Message() {
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
