package com.example.libraryapp.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "google.books", ignoreUnknownFields = false)
@Validated
public record GoogleBooksApiConfiguration(
        @NotBlank
        String url,
        @NotNull
        boolean enabled,
        @NotNull
        String key
) {

}
