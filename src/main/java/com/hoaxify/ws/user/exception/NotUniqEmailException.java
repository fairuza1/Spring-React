package com.hoaxify.ws.user.exception;

import com.hoaxify.ws.shared.Messages;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Collections;

import java.util.Map;

public class NotUniqEmailException extends RuntimeException {

public NotUniqEmailException() {
    super(Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale()));
}
public Map<String,String>getValidationErrors() {
    return Collections.singletonMap("email", Messages.getMessageForLocale("hoaxify.constraint.email.notunique", LocaleContextHolder.getLocale()));
//böylece map olusturdu properties e mails oldu value de message oldu
}
}
