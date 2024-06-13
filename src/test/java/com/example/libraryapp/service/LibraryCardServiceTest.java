package com.example.libraryapp.service;

import com.example.libraryapp.exception.LibraryCardNotFoundException;
import com.example.libraryapp.model.LibraryCard;
import com.example.libraryapp.model.User;
import com.example.libraryapp.repository.LibraryCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibraryCardServiceTest {

    @Mock
    private LibraryCardRepository libraryCardRepository;

    @InjectMocks
    private LibraryCardService libraryCardService;

    @Test
    void findLibraryCardByCardNumberReturnsCorrectCardNumber() {
        // given
        int libraryCardNumber = 999000;
        LibraryCard libraryCardFromDatabase = new LibraryCard(
                1,
                new User(),
                999000,
                new ArrayList<>(),
                true);
        // when
        when(libraryCardRepository.findLibraryCardByLibraryCardNumber(libraryCardNumber))
                .thenReturn(Optional.of(libraryCardFromDatabase));
        LibraryCard libraryCard = libraryCardService.findLibraryCardByCardNumber(libraryCardNumber);
        // then
        assertAll("library card",
                () -> assertEquals(libraryCardFromDatabase.getId(), libraryCard.getId()),
                () -> assertEquals(libraryCardFromDatabase.getLibraryCardNumber(), libraryCard.getLibraryCardNumber()));
    }

    @Test
    void findLibraryCardByCardNumberThrowsLibraryCardNotFoundExceptionWhenCardNumberIsIncorrect() {
        // given
        int libraryCardNumber = 100;
        // when
        when(libraryCardRepository.findLibraryCardByLibraryCardNumber(libraryCardNumber))
                .thenReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> libraryCardService.findLibraryCardByCardNumber(libraryCardNumber))
                .isInstanceOf(LibraryCardNotFoundException.class)
                .hasMessage("No library card found with the following number: %d"
                        .formatted(libraryCardNumber));
    }
}
