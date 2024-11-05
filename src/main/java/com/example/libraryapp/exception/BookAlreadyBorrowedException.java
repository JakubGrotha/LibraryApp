package com.example.libraryapp.exception;

public class BookAlreadyBorrowedException extends RuntimeException {

    public BookAlreadyBorrowedException(String message) {
        super(message);
    }
}
