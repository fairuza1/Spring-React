package com.hoaxify.ws.user;

import com.hoaxify.ws.user.validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
//spirng data jpa bu callsın data basede tablo karşılı olduğunu karşılayacak yoksada orda yaratacak bu default davranıştır
@Table(name = "users",uniqueConstraints =@UniqueConstraint(columnNames = {"email"})  )
public class User {
    @Id
    @GeneratedValue//veri tabanında kendi artma yapmasına yaradı
    long id;

    @NotBlank//boş olamaması için bunu ekliyoruz
    @Size(min = 4, max = 255)
    String username;

    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    String password;


    @NotBlank
    @Email
    @UniqueEmail
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
