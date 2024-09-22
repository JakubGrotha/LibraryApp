package com.example.libraryapp.model;

import java.util.List;

public record GoogleBooksDetails(
        List<Volume> items
) {

    public record Volume(
            VolumeInfo volumeInfo
    ) {

        public record VolumeInfo(
                String title,
                List<String> authors,
                String publisher,
                int pageCount,
                String language
        ) {

        }
    }
}
