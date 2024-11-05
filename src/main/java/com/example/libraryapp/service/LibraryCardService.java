package com.example.libraryapp.service;

import com.example.libraryapp.exception.LibraryCardNotFoundException;
import com.example.libraryapp.model.LibraryCard;
import com.example.libraryapp.repository.LibraryCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryCardService {

    private final LibraryCardRepository libraryCardRepository;

    public LibraryCard findLibraryCardByCardNumber(int libraryCardNumber) {
        return libraryCardRepository.findLibraryCardByLibraryCardNumber(libraryCardNumber)
                .orElseThrow(() -> new LibraryCardNotFoundException("No library card found with the following number: %d"
                        .formatted(libraryCardNumber)));
    }
}
