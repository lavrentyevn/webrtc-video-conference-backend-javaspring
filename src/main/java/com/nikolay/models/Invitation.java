package com.nikolay.models;


import jakarta.persistence.*;

@Entity
@Table(name = "invitation")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinColumn(
            name = "user_model_id",
            referencedColumnName = "id"
    )
    private UserModel userModel;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinColumn(
            name = "room_id",
            referencedColumnName = "id"
    )
    private Room room;


    public Invitation() {
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
