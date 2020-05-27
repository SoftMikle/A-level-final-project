package com.alevel.library.rest;

import com.alevel.library.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Validated
class ControllerAdviceImpl extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {JwtAuthenticationException.class})
    ResponseEntity<Object> handleJwtAuthenticationException(JwtAuthenticationException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {UserConflictException.class})
    ResponseEntity<Object> handleUserConflictException(UserConflictException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {PasswordValidationException.class})
    ResponseEntity<Object> handlePasswordValidationException(PasswordValidationException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {
            BookStatusException.class,
            BookNotFoundException.class,
            UserNotFoundException.class,
            ClientNotFoundException.class,
            UsernameNotFoundException.class,
            ClientCardNotFoundException.class,
            ClientAccountNotFoundException.class,
            ClientCardItemNotFoundException.class
    })
    ResponseEntity<Object> handleBadRequestExceptions(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
