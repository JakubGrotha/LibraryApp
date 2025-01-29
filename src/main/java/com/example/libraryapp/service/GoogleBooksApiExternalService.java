package com.example.libraryapp.service;

import com.example.libraryapp.model.GoogleBooksDetails;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface GoogleBooksApiExternalService {

    @GetExchange(accept = "application/json")
    GoogleBooksDetails getGoogleBooksDetails(@RequestParam("q") String query, @RequestParam("key") String key);
}
