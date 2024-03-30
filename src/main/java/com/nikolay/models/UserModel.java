package com.nikolay.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_model")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;

    @Column(
            unique = true,
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String email;

    @Column(
            columnDefinition = "VARCHAR",
            name = "refresh_token"
    )
    private String refreshToken;

    @Column
    private boolean verified;

    @Column(
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE",
            name = "created_at"
    )
    private LocalDateTime createdAt;

    @OneToOne(
            mappedBy = "userModel",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Guest guest;

    @OneToOne(
            mappedBy = "userModel",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Client client;

    @OneToMany(
            mappedBy = "userModel",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<EmailVerification> emailVerification = new ArrayList<>();

    @OneToMany(
            mappedBy = "userModel",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Room> room = new ArrayList<>();

    @OneToMany(
            mappedBy = "userModel",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Message> message = new ArrayList<>();

    @OneToMany(
            mappedBy = "userModel",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<EventLog> eventLog = new ArrayList<>();

    @OneToMany(
            mappedBy = "userModel",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Invitation> invitation = new ArrayList<>();

    public UserModel() {
    }

    public UserModel(String email, String refresh_token, boolean verified, LocalDateTime created_at) {
        this.email = email;
        this.refreshToken = refresh_token;
        this.verified = verified;
        this.createdAt = created_at;
    }

    public UserModel(String email, boolean verified, LocalDateTime created_at) {
        this.email = email;
        this.verified = verified;
        this.createdAt = created_at;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void addRoom(Room room) {
        if (!this.room.contains(room)) {
            this.room.add(room);
            room.setUserModel(this);
        }
    }

    public void addMessage(Message message) {
        if (!this.message.contains(message)) {
            this.message.add(message);
            message.setUserModel(this);
        }
    }

    public void addInvitation(Invitation invitation) {
        if (!this.invitation.contains(invitation)) {
            this.invitation.add(invitation);
            invitation.setUserModel(this);
        }
    }

    public void addEventLog(EventLog eventLog) {
        if (!this.eventLog.contains(eventLog)) {
            this.eventLog.add(eventLog);
            eventLog.setUserModel(this);
        }
    }

    public void addEmailVerification(EmailVerification emailVerification) {
        if (!this.emailVerification.contains(emailVerification)) {
            this.emailVerification.add(emailVerification);
            emailVerification.setUserModel(this);
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return client.getPassword();
    }

    @Override
    public String getUsername() {
        if (client == null) return "Guest";
        return client.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
}
