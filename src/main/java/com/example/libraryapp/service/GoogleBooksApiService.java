package com.example.libraryapp.service;

import com.example.libraryapp.configuration.GoogleBooksApiConfiguration;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.model.GoogleBooksDetails;
import com.example.libraryapp.model.GoogleBooksDetails.Volume.VolumeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

import static com.example.libraryapp.model.GoogleBooksDetails.Volume;


@Service
@RequiredArgsConstructor
public class GoogleBooksApiService {

    private final GoogleBooksApiConfiguration configuration;
    private final RestClient restClient;

    public Optional<BookDetails> findBookDetailsInGoogleBooksApiUsingIsbn(String isbn) {
        if (!configuration.enabled()) {
            return Optional.empty();
        }
        String apiKey = configuration.key();
        ResponseEntity<GoogleBooksDetails> response = restClient.get()
                .uri("https://www.googleapis.com/books/v1/volumes?q=isbn:%s&keyes&key=%s".formatted(isbn, apiKey))
                .retrieve()
                .toEntity(GoogleBooksDetails.class);
        return Optional.of(response)
                .map(ResponseEntity::getBody)
                .flatMap(it -> it.items().stream().findFirst())
                .map(Volume::volumeInfo)
                .map(it -> createBookDetails(isbn, it));
    }

    private BookDetails createBookDetails(String isbn, VolumeInfo volumeInfo) {
        return BookDetails.builder()
                .isbn(isbn)
                .author(volumeInfo.authors().getFirst())
                .title(volumeInfo.title())
                .publisher(volumeInfo.publisher())
                .numberOfPages(volumeInfo.pageCount())
                .language(volumeInfo.language())
                .build();
    }
}
