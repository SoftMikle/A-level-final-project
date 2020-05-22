package com.alevel.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@org.springframework.web.bind.annotation.ControllerAdvice
//public class ControllerAdvice {
//
//    @ExceptionHandler(value = {JwtAuthenticationException.class})
//    ResponseEntity handleAuthExceptions(){
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is unauthorized");
//    }
//
//    @ExceptionHandler(value = {ClientNotFoundException.class})
//    ResponseEntity handleExceptions(){
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is unauthorized");
//    }
//
//}
