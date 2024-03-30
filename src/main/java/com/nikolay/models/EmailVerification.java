package com.nikolay.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_verification")
public class EmailVerification {

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

    @Column(
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE",
            name = "sent_at"
    )
    private LocalDateTime sentAt;

    public EmailVerification(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public EmailVerification() {
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
