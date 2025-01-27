package com.example.libraryapp.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "google.books", ignoreUnknownFields = false)
@Validated
public record GoogleBooksApiConfiguration(
        @NotNull
        boolean enabled,
        @NotNull
        String key
) {

}
