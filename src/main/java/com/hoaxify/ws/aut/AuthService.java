package com.hoaxify.ws.aut;

import com.hoaxify.ws.aut.dto.AuthResponse;
import com.hoaxify.ws.aut.dto.Credentials;
import com.hoaxify.ws.aut.exception.AuthenticationException;
import com.hoaxify.ws.aut.token.Token;
import com.hoaxify.ws.aut.token.TokenService;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserService;

import com.hoaxify.ws.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    TokenService tokenService;

    public AuthResponse authenticate(Credentials creds) {
        User inDB = userService.findByEmail(creds.email());
        if (inDB == null) throw new AuthenticationException();
        if (!passwordEncoder.matches(creds.password(), inDB.getPassword())) throw new AuthenticationException();
        Token token = tokenService.createToken(inDB, creds);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUser(new UserDto(inDB));

        return authResponse;

    }
}
