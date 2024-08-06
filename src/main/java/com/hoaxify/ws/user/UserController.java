package com.hoaxify.ws.user;


import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.dto.UserCreate;
import com.hoaxify.ws.user.dto.UserDto;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.*;




@RestController
public class UserController {
    @Autowired //kullanmak istediğimiz bir clasın bize verilmesi için kullandığımız bir antotion dependecy enjection dur
    UserService userService; //

    //  @CrossOrigin //bunuda bakalıcak ve gerek kalmadı buna çünkü frontend kısımında proxy ayarı yapıldı
    @PostMapping("/api/v1/users")
    GenericMessage createUser(@Valid @RequestBody UserCreate user) { // usercreate olmayan fieldlar tehlke olmuyo çünkü sadece var olan usercreate fieldları usere mapliyoruz

        userService.save(user.toUser());
        //burada ise user.touser methodu sayesinde inakfif oldu
        String message = Messages.getMessageForLocale("hoaxify.create.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @PatchMapping("/api/v1/users/{token}/active")
//entitynin sadece belli bir kısmını değişttirmek için nu kullanılır burada da userda ki activation falsedan trueya çekeceğim
//cliente gönderilen bir token var onu ıdentiy olarak kullanacağız zaten bu token uniqe olacağı için kulalnıcı ile ilişkisi var

    GenericMessage activateUser(@PathVariable String token) { //token e erişmek için pathvariable anatasyonu ile erişebiliriz
        userService.activateUser(token);
        String message = Messages.getMessageForLocale("hoaxify.activate.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    //birden fazla kullancıı oldugğu için listelmek için bir and point eklendi bunun içinde http methodu ise get methodu
    @GetMapping("/api/v1/users")
    Page<UserDto> getUsers(Pageable page) {//bu controllerden elimizle kaç tane veri girmek yerine bu request  bzie belirli bir miktar sayi girmemizi sağlar
        return userService.getUsers(page).map(UserDto::new);
    }

    @GetMapping("api/v1/users/{id}")
    UserDto getUserById(@PathVariable Long id) {
        return new UserDto(userService.getUser(id));
    }






// 400 hata mesajları http client hatasını ifade eder 500 ise server hatasını ifade eder
}
