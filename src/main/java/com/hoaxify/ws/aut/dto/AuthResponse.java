package com.hoaxify.ws.aut.dto;

import com.hoaxify.ws.aut.token.Token;
import com.hoaxify.ws.user.dto.UserDto;

public class AuthResponse {
    UserDto user;

    Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
    //token ve dto objesi olacak
}
