package com.example.libraryapp.exception;

public class BookDetailsNotFoundException extends RuntimeException {

    public BookDetailsNotFoundException(String message) {
        super(message);
    }
}
