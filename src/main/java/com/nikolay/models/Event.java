package com.nikolay.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinColumn(
            name = "room_id",
            referencedColumnName = "id"
    )
    private Room room;

    @OneToMany(
            mappedBy = "event",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<EventLog> eventLog = new ArrayList<>();

    public Event() {
    }

    public Event(Room room) {
        this.room = room;
    }

    public void addEventLog(EventLog eventLog) {
        if (!this.eventLog.contains(eventLog)) {
            this.eventLog.add(eventLog);
            eventLog.setEvent(this);
        }
    }

    public int getId() {
        return id;
    }
}
