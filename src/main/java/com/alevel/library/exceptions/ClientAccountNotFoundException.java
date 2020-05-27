package com.alevel.library.exceptions;

public class ClientAccountNotFoundException extends RuntimeException {
    public ClientAccountNotFoundException(String message) {
        super(message);
    }
}
