package com.hoaxify.ws.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoaxify.ws.user.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class userDto {

    long id;

    String username;

    String email;


    String image;

    public userDto(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        setEmail(user.getEmail());

    }
    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


}
