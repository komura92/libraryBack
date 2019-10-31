package com.example.library.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User doesn't exists!")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) {
        super("User with login " + login + " doesn't exists!");
    }
}
