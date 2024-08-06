package com.hoaxify.ws.error;

import com.hoaxify.ws.aut.exception.AuthenticationException;
import com.hoaxify.ws.shared.Messages;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.InvalidTokenException;
import com.hoaxify.ws.user.exception.NotFoundException;
import com.hoaxify.ws.user.exception.NotUniqEmailException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            NotUniqEmailException.class,
            ActivationNotificationException.class,
            InvalidTokenException.class,
            NotFoundException.class,
            AuthenticationException.class


    })
    ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(exception.getMessage());
        if (exception instanceof MethodArgumentNotValidException) {
            String message = Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale());

            apiError.setMessage(message);
            apiError.setStatus(400);
            var validationErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacing) -> existing));
            apiError.setValidationErrors(validationErrors);
        } else if (exception instanceof NotUniqEmailException) {
            apiError.setStatus(400);
            apiError.setValidationErrors(((NotUniqEmailException) exception).getValidationErrors());

        } else if (exception instanceof ActivationNotificationException) {
            apiError.setStatus(502);
        } else if (exception instanceof InvalidTokenException) {
            apiError.setStatus(400);
        } else if (exception instanceof NotFoundException) {
            apiError.setStatus(404);
        } else if (exception instanceof AuthenticationException) {
            apiError.setStatus(401);
        }

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }


//    @ExceptionHandler(NotUniqEmailException.class)
//    ResponseEntity<ApiError> handleNotUniqueEmailEx(NotUniqEmailException exception) {
//        ApiError apiError = new ApiError();
//        apiError.setPath("/api/v1/users");
//        apiError.setMessage(exception.getMessage());
//        apiError.setStatus(400);
//        apiError.setValidationErrors(exception.getValidationErrors());
//        return ResponseEntity.status(400).body(apiError);
//    }

//    @ExceptionHandler(ActivationNotificationException.class)
//    ResponseEntity<ApiError> handleActivationNotificationException(ActivationNotificationException exception) {
//        ApiError apiError = new ApiError();
//        apiError.setPath("/api/v1/users");
//        apiError.setMessage(exception.getMessage());
//        apiError.setStatus(502);
//        return ResponseEntity.status(502).body(apiError);
//    }

//    @ExceptionHandler(InvalidTokenException.class)
//    ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException exception, HttpServletRequest request) {
//        ApiError apiError = new ApiError();
//        apiError.setPath(request.getRequestURI());
//        apiError.setMessage(exception.getMessage());
//        apiError.setStatus(400);
//        return ResponseEntity.status(400).body(apiError);
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    ResponseEntity<ApiError> handleEntityNotFoundException(NotFoundException exception, HttpServletRequest request) {
//        ApiError apiError = new ApiError();
//        apiError.setPath(request.getRequestURI());
//        apiError.setMessage(exception.getMessage());
//        apiError.setStatus(404);
//        return ResponseEntity.status(404).body(apiError);
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    ResponseEntity<?> handleAuthenticationException(AuthenticationException exception) {
//        ApiError error = new ApiError();
//        error.setPath("/api/v1/auth");
//        error.setStatus(401);
//        error.setMessage(exception.getMessage());
//        return ResponseEntity.status((401)).body(error);
//
//    }
//
}
