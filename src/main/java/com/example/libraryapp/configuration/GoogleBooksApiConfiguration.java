package com.example.libraryapp.configuration;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "google.books", ignoreUnknownFields = false)
@Validated
@Getter
@Setter
public class GoogleBooksApiConfiguration {

    @NotNull
    private boolean enabled;
    @NotNull
    private String key;
}
