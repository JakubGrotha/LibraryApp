package com.example.libraryapp.model.google_books;

public record Volume(String kind,
                     String id,
                     String etag,
                     String selfLink,
                     VolumeInfo volumeInfo) {

}
