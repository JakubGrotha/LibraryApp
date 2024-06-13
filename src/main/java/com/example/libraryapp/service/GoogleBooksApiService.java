package com.example.libraryapp.service;

import com.example.libraryapp.configuration.GoogleBooksApiConfiguration;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.model.googlebooks.GoogleBooksDetails;
import com.example.libraryapp.model.googlebooks.Volume;
import com.example.libraryapp.model.googlebooks.VolumeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class GoogleBooksApiService {

    private final GoogleBooksApiConfiguration configuration;
    private final RestClient restClient;

    public BookDetails findBookDetailsInGoogleBooksApiUsingIsbn(String isbn) {
        if (!configuration.isEnabled()) {
            return new BookDetails();
        }
        String apiKey = configuration.getKey();
        ResponseEntity<GoogleBooksDetails> response = restClient.get()
                .uri("https://www.googleapis.com/books/v1/volumes?q=isbn:%s&keyes&key=%s".formatted(isbn, apiKey))
                .retrieve()
                .toEntity(GoogleBooksDetails.class);

        GoogleBooksDetails googleBooksDetails = response.getBody();
        if (googleBooksDetails == null) {
            return new BookDetails();
        }
        VolumeInfo volumeInfo = googleBooksDetails.items()
                .stream()
                .findFirst()
                .map(Volume::volumeInfo)
                .orElse(null);
        return volumeInfo == null
                ? new BookDetails()
                : BookDetails.builder()
                    .isbn(isbn)
                    .author(volumeInfo.authors().getFirst())
                    .title(volumeInfo.title())
                    .publisher(volumeInfo.publisher())
                    .numberOfPages(volumeInfo.pageCount())
                    .language(volumeInfo.language())
                    .build();
    }
}
