package com.example.library.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Incorrect password")
public class BadPasswordException extends RuntimeException {
    public BadPasswordException(String username) {
        super("Incorrect password for user " + username);
    }
}
