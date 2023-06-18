package com.example.libraryapp.model.google_books;

import java.util.List;

public record GoogleBooksDetails(String kind,
                                 int totalItems,
                                 List<Volume> items) {

}
