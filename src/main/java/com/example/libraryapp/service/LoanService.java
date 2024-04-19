package com.example.libraryapp.service;

import com.example.libraryapp.exception.LoanNotFoundException;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.LibraryCard;
import com.example.libraryapp.model.Loan;
import com.example.libraryapp.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.example.libraryapp.service.LoanService.LoanResult.*;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final LibraryCardService libraryCardService;

    public LoanResult loanBook(String barcode, int libraryCardNumber, int durationInMonths) {
        Book bookToLoan = bookService.findBookByBarcode(barcode);
        LibraryCard libraryCard = libraryCardService.findLibraryCardByCardNumber(libraryCardNumber);

        if (!bookToLoan.isAvailable()) {
            return new Failure(STR."Book with the following barcode has already been borrowed: \{barcode}");
        }

        Loan loan = Loan.builder()
                .book(bookToLoan)
                .loanDate(LocalDate.now())
                .returnDate(LocalDate.now().plusMonths(durationInMonths))
                .libraryCard(libraryCard)
                .build();

        libraryCard.addNewLoan(loan);
        libraryCardService.updateLibraryCard(libraryCard);
        loanRepository.save(loan);

        bookToLoan.setLoan(loan);
        bookToLoan.setAvailable(false);
        bookService.updateBook(bookToLoan);
        return new Success();
    }

    public void updateLoan(Loan loan) {
        loanRepository.save(loan);
    }

    public Loan findLoanById(long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("No loan found with the id: %d".formatted(id)));
    }

    public void deleteLoanById(long id) {
        loanRepository.deleteById(id);
    }

    public sealed interface LoanResult permits Success, Failure {
        record Success() implements LoanResult {
        }

        record Failure(String message) implements LoanResult {
        }
    }

}
