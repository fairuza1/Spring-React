package com.hoaxify.ws.configuration;

import ch.qos.logback.core.net.server.Client;
import jakarta.validation.constraints.Email;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "hoaxify")//hoaxify ile başlayan configler burada tutulacak
@Configuration//prefixteki propertileri clasın değerleri  a sing edecek

public class HoaxifyProperties {
    private Email email;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private Client client;

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }


    public static record Email(
            String username,
            String password,
            String host,
            String from,
            int port
    ) {}
    public  static record Client(
            String host
    ){}

}
