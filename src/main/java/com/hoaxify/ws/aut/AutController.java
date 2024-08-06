package com.hoaxify.ws.aut;

import com.hoaxify.ws.aut.dto.AuthResponse;
import com.hoaxify.ws.aut.dto.Credentials;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutController {

    @Autowired
    AuthService authService;


    @PostMapping("/api/v1/auth")
    AuthResponse handleAuthentication(@Valid @RequestBody Credentials creds) {
        return authService.authenticate(creds);

    }


}
