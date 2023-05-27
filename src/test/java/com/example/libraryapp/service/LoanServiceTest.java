package com.example.libraryapp.service;

import com.example.libraryapp.exception.LoanNotFoundException;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.LibraryCard;
import com.example.libraryapp.model.Loan;
import com.example.libraryapp.repository.LoanRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoanServiceTest {
    private LoanRepository loanRepository;
    private LoanService loanService;

    @BeforeEach
    void setup() {
        loanRepository = mock(LoanRepository.class);
        loanService = new LoanService(loanRepository);
    }

    @Test
    void findLoanByIdReturnsCorrectLoanObject() {
        // given
        long loanId = 1L;
        Loan loanFromDatabase = new Loan(
                loanId,
                new LibraryCard(),
                new Book(),
                LocalDate.of(2023, 7, 10),
                LocalDate.of(2023, 10, 10)
        );
        // when
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loanFromDatabase));
        Loan loan = loanService.findLoanById(loanId);
        // then
        assertAll("loan",
                () -> assertEquals(loanFromDatabase.getId(), loan.getId()),
                () -> assertEquals(loanFromDatabase.getLoanDate(), loan.getLoanDate()),
                () -> assertEquals(loanFromDatabase.getReturnDate(), loan.getReturnDate())
        );
    }

    @Test
    void findLoanByIdThrowsCorrectException() {
        // given
        long loanId = 0L;
        // when
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());
        // then
        Assertions.assertThatThrownBy(() -> loanService.findLoanById(loanId))
                .isInstanceOf(LoanNotFoundException.class)
                .hasMessage("No loan found with the id: %d".formatted(loanId));
    }
}
