package com.example.libraryapp.repository;

import com.example.libraryapp.model.LibraryCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryCardRepositoryTest {

    @Autowired
    private LibraryCardRepository libraryCardRepository;

    @Test
    void findLibraryCardByLibraryCardNumberReturnsCorrectLibraryCardObject() {
        // given
        int libraryCardNumber = 999111;
        // when
        Optional<LibraryCard> libraryCard = libraryCardRepository
                .findLibraryCardByLibraryCardNumber(libraryCardNumber);
        // then
        assertTrue(libraryCard.isPresent());
        assertEquals(2, libraryCard.get().getUser().getId());
    }

    @Test
    void findLibraryCardByLibraryCardNumberReturnsEmptyOptionalIfTheNumberIsNotFound() {
        // given
        int libraryCardNumber = 19191999;
        // when
        Optional<LibraryCard> libraryCard = libraryCardRepository
                .findLibraryCardByLibraryCardNumber(libraryCardNumber);
        // then
        assertFalse(libraryCard.isPresent());
    }
}
