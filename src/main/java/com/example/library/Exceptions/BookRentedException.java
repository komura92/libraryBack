package com.example.library.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "This book is already rented!")
public class BookRentedException extends RuntimeException {
    public BookRentedException(Long id) {
        super("Book with id " + Long.toString(id) + " is already rented!");
    }
}
