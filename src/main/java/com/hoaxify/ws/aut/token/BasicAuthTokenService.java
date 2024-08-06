package com.hoaxify.ws.aut.token;

import com.hoaxify.ws.aut.dto.Credentials;
import com.hoaxify.ws.user.User;
import org.springframework.stereotype.Service;

import java.util.Base64;
@Service
public class BasicAuthTokenService implements TokenService {
    @Override
    public Token createToken(User user, Credentials creds) {
        String emailColonPassword = creds.email() + ":" + creds.password();

        String token = Base64.getEncoder().encodeToString(emailColonPassword.getBytes());

        return new Token("basic", token);

    }



    @Override
    public User verifyToken(String authorizationHeader) {
        return null;
    }
}
