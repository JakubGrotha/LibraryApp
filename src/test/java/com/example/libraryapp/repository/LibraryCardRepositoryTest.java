package com.example.libraryapp.repository;

import com.example.libraryapp.model.LibraryCard;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.TestConstructor.AutowireMode.*;

@SpringBootTest
@TestConstructor(autowireMode = ALL)
class LibraryCardRepositoryTest {

    private final LibraryCardRepository libraryCardRepository;

    LibraryCardRepositoryTest(LibraryCardRepository libraryCardRepository) {
        this.libraryCardRepository = libraryCardRepository;
    }

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
