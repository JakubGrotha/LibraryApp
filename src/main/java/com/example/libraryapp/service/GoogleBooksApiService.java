package com.example.libraryapp.service;

import com.example.libraryapp.configuration.GoogleBooksApiConfiguration;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.model.GoogleBooksDetails;
import com.example.libraryapp.model.GoogleBooksDetails.Volume.VolumeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.libraryapp.model.GoogleBooksDetails.Volume;


@Service
@RequiredArgsConstructor
public class GoogleBooksApiService {

    private final GoogleBooksApiConfiguration configuration;
    private final GoogleBooksApiExternalService googleBooksApiExternalService;

    public Optional<BookDetails> findBookDetailsInGoogleBooksApiUsingIsbn(String isbn) {
        if (!configuration.enabled()) {
            return Optional.empty();
        }
        String query = "isbn:%s&keyes".formatted(isbn);
        String apiKey = configuration.key();
        GoogleBooksDetails response = googleBooksApiExternalService.getGoogleBooksDetails(query, apiKey);
        return Optional.of(response)
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
