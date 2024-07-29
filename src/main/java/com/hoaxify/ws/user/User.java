package com.hoaxify.ws.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
//spirng data jpa bu callsın data basede tablo karşılı olduğunu karşılayacak yoksada orda yaratacak bu default davranıştır
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue//veri tabanında kendi artma yapmasına yaradı
    long id;
    String username;
    String password;
    String email;

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