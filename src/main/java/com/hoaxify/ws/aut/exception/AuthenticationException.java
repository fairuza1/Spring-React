package com.hoaxify.ws.aut.exception;

import com.hoaxify.ws.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super(Messages.getMessageForLocale("hoaxify.auth.invalid.credentials", LocaleContextHolder.getLocale()));

    }
}
