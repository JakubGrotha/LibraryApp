package com.example.libraryapp.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "google.books", ignoreUnknownFields = false)
@Validated
@Data
public class GoogleBooksApiConfiguration {

    @NotNull
    private boolean enabled;

    @NotNull
    private String key;
}
