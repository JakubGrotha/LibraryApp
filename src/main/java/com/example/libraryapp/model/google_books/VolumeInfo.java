package com.example.libraryapp.model.google_books;

import java.util.List;

public record VolumeInfo(String title,
                         String subtitle,
                         List<String> authors,
                         String publisher,
                         String publishedDate,
                         String description,
                         int pageCount,
                         String printType,
                         List<String> categories,
                         double averageRating,
                         int ratingsCount,
                         String language,
                         String previewLink,
                         String infoLink,
                         String canonicalVolumeLink) {

}
