package com.hoaxify.ws.user;


import com.hoaxify.ws.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired //kullanmak istediğimiz bir clasın bize verilmesi için kullandığımız bir antotion dependecy enjection dur
    UserService userService; //

    //  @CrossOrigin //bunuda bakalıcak ve gerek kalmadı buna çünkü frontend kısımında proxy ayarı yapıldı
    @PostMapping("/api/v1/users")
    GenericMessage createUser(@RequestBody User user) {
        userService.save(user);//requeste gelen jason bizim bizim user objeye mapleniyor
return new GenericMessage("user is creatted");

    }


}
