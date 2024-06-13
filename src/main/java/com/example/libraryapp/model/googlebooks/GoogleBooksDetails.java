package com.example.libraryapp.model.googlebooks;

import java.util.List;

public record GoogleBooksDetails(
        String kind,
        int totalItems,
        List<Volume> items
) {
}
