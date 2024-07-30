package com.hoaxify.ws.user;


import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired //kullanmak istediğimiz bir clasın bize verilmesi için kullandığımız bir antotion dependecy enjection dur
    UserService userService; //

    //  @CrossOrigin //bunuda bakalıcak ve gerek kalmadı buna çünkü frontend kısımında proxy ayarı yapıldı
    @PostMapping("/api/v1/users")
    GenericMessage createUser(@Valid @RequestBody User user) {
        userService.save(user);//requeste gelen jason bizim bizim user objeye mapleniyor
        return new GenericMessage("kullanıcı oluşturuldu");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleMethodArgtNotValidEx(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation error");
        apiError.setStatus(400);
        var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,(existing,replacing)->existing));
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqEmailException.class)
    ResponseEntity<ApiError> handleNotUniqEmailEx(NotUniqEmailException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation error");
        apiError.setStatus(400);
        Map<String,String>validationErrors = new HashMap<>();
        validationErrors.put("email","E-mail kullanınız");
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

}
//        ApiError apiError = new ApiError();
//        apiError.setPath("/api/v1/users");
//        apiError.setMessage("Validation error");
//        apiError.setStatus(400);
//        Map<String, String> validationErrors = new HashMap<>();
//        if (user.getUsername() == null || user.getUsername().isEmpty()) {
////burada ise hatalı giriş yaptığında ismi null girdiğinde ve boşş girdiğinde status400 hatası karşılayacak
//            validationErrors.put("username", "username cannot be null!!");
//        }
//        if (user.getEmail() == null || user.getEmail().isEmpty()) {
//            validationErrors.put("email", "email alanı boş olamaz!!! ");
//        }
//        if (validationErrors.size() > 0) {
//
//            apiError.setValidationErrors(validationErrors);
//            return ResponseEntity.badRequest().body(apiError);
//        }


//        Map<String, String> validationErrors = new HashMap<>();
//        for (var fieldError : exception.getBindingResult().getFieldErrors()) {
//            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
//
//        }