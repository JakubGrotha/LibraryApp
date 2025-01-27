package com.example.libraryapp;

import com.example.libraryapp.configuration.GoogleBooksApiConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = GoogleBooksApiConfiguration.class)
public class LibraryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryAppApplication.class, args);
    }
}
