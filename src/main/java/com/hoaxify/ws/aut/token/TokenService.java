package com.hoaxify.ws.aut.token;


import com.hoaxify.ws.aut.dto.Credentials;
import com.hoaxify.ws.user.User;

public interface TokenService {
    public Token createToken(User user, Credentials creds);

public User verifyToken(String authorizationHeader);


}
