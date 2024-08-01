package com.hoaxify.ws.user;


import jakarta.persistence.*;


@Entity
//spirng data jpa bu callsın data basede tablo karşılı olduğunu karşılayacak yoksada orda yaratacak bu default davranıştır
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {
    @Id
    @GeneratedValue//veri tabanında kendi artma yapmasına yaradı
    long id;


    String username;

    String password;

    String email;

    boolean active = false;

    String activationToken;


    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
