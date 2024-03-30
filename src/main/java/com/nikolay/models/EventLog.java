package com.nikolay.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_log")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinColumn(
            name = "event_id",
            referencedColumnName = "id"
    )
    private Event event;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinColumn(
            name = "user_model_id",
            referencedColumnName = "id"
    )
    private UserModel userModel;

    @Column(
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE",
            name = "joined_at"
    )
    private LocalDateTime joinedAt;

    @Column(
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE",
            name = "left_at"
    )
    private LocalDateTime leftAt;

    public EventLog() {
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventLog(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Event getEvent() {
        return event;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
    }
}
