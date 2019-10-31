package com.example.library.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Book is not rented for you!")
public class BookNotRentedForUserException extends RuntimeException {
    public BookNotRentedForUserException(Long id) {
        super("Book with id " + Long.toString(id) + " was not rented for you!");
    }
}
