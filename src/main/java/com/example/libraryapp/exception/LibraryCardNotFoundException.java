package com.example.libraryapp.exception;

public class LibraryCardNotFoundException extends RuntimeException {

    public LibraryCardNotFoundException(String message) {
        super(message);
    }
}
