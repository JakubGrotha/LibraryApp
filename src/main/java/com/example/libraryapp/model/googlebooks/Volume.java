package com.example.libraryapp.model.googlebooks;

public record Volume(
        String kind,
        String id,
        String etag,
        String selfLink,
        VolumeInfo volumeInfo
) {
}
