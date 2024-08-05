package com.hoaxify.ws.user;


import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.dto.UserCreate;
import com.hoaxify.ws.user.dto.UserDto;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.InvalidTokenException;
import com.hoaxify.ws.user.exception.NotFoundException;
import com.hoaxify.ws.user.exception.NotUniqEmailException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.stream.Collectors;

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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(400);
        var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacing) -> existing));
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqEmailException.class)
    ResponseEntity<ApiError> handleNotUniqueEmailEx(NotUniqEmailException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(400);
        apiError.setValidationErrors(exception.getValidationErrors());
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler(ActivationNotificationException.class)
    ResponseEntity<ApiError> handleActivationNotificationException(ActivationNotificationException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(502);
        return ResponseEntity.status(502).body(apiError);
    }

    @ExceptionHandler(InvalidTokenException.class)
    ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(400);
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApiError> handleEntityNotFoundException(NotFoundException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(404);
        return ResponseEntity.status(404).body(apiError);
    }


// 400 hata mesajları http client hatasını ifade eder 500 ise server hatasını ifade eder
}
