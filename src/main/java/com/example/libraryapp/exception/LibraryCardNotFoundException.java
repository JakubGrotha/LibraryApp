package com.example.libraryapp.exception;

import java.io.IOException;

public class LibraryCardNotFoundException extends RuntimeException {
    public LibraryCardNotFoundException(String message) {
        super(message);
    }
}
