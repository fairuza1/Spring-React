package com.hoaxify.ws.user;


import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired //kullanmak istediğimiz bir clasın bize verilmesi için kullandığımız bir antotion dependecy enjection dur
    UserService userService; //

    //  @CrossOrigin //bunuda bakalıcak ve gerek kalmadı buna çünkü frontend kısımında proxy ayarı yapıldı
    @PostMapping("/api/v1/users")
    ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation error");
        apiError.setStatus(400);
        Map<String, String> validationErrors = new HashMap<>();
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
//burada ise hatalı giriş yaptığında ismi null girdiğinde ve boşş girdiğinde status400 hatası karşılayacak
            validationErrors.put("username", "username cannot be null");
        }
        if(user.getEmail()==null || user.getEmail().isEmpty()) {
            validationErrors.put("email", "email alanı boş olamaz ");
        }
        if (validationErrors.size() > 0) {

            apiError.setValidationErrors(validationErrors);
            return ResponseEntity.badRequest().body(apiError);
        }
        userService.save(user);//requeste gelen jason bizim bizim user objeye mapleniyor
        return ResponseEntity.ok(new GenericMessage("kullanıcı oluşturuldu"));

    }


}
