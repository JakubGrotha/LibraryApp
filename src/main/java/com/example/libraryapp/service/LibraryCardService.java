package com.example.libraryapp.service;

import com.example.libraryapp.exception.LibraryCardNotFoundException;
import com.example.libraryapp.model.LibraryCard;
import com.example.libraryapp.repository.LibraryCardRepository;
import org.springframework.stereotype.Service;

@Service
public class LibraryCardService {

    private final LibraryCardRepository libraryCardRepository;

    public LibraryCardService(LibraryCardRepository libraryCardRepository) {
        this.libraryCardRepository = libraryCardRepository;
    }

    public void updateLibraryCard(LibraryCard libraryCard) {
        libraryCardRepository.save(libraryCard);
    }

    public LibraryCard findLibraryCardByCardNumber(int libraryCardNumber) {
        return libraryCardRepository.findLibraryCardByLibraryCardNumber(libraryCardNumber)
                .orElseThrow(() -> new LibraryCardNotFoundException("No library card found with the following number: %d".formatted(libraryCardNumber)));
    }
}
