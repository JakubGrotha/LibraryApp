package com.example.libraryapp.service;

import com.example.libraryapp.exception.BookAlreadyBorrowedException;
import com.example.libraryapp.exception.LoanNotFoundException;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.LibraryCard;
import com.example.libraryapp.model.Loan;
import com.example.libraryapp.repository.LoanRepository;
import com.example.libraryapp.utils.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Period;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.FailedException;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final LibraryCardService libraryCardService;
    private final TimeProvider timeProvider;

    @Transactional
    public void loanBook(String barcode, int libraryCardNumber, Period loanDuration) {
        try (var scope = StructuredTaskScope.open()) {
            var bookToLoanSubtask = scope.fork(() -> bookService.findBookByBarcode(barcode));
            var libraryCardSubtask = scope.fork(() -> libraryCardService.findLibraryCardByCardNumber(libraryCardNumber));
            scope.join();

            Book bookToLoan = bookToLoanSubtask.get();
            LibraryCard libraryCard = libraryCardSubtask.get();
            if (!bookToLoan.isAvailable()) {
                throw new BookAlreadyBorrowedException(
                        "Book with the following barcode has already been borrowed: %s".formatted(barcode));
            }
            Loan loan = Loan.create(libraryCard, bookToLoan, timeProvider, loanDuration);
            loanRepository.save(loan);
            updateBookToLoan(bookToLoan, loan);
        } catch (InterruptedException e) {
            System.out.println("Interrupted"); // TODO: Add logging
            throw new RuntimeException(e);
        } catch (FailedException e) {
            System.out.println("Failed exception"); // TODO: Add logging
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void registerReturn(String barcode) {
        Book bookToReturn = bookService.findBookByBarcode(barcode);
        Loan loan = bookToReturn.getLoan();
        if (loan == null) {
            throw new RuntimeException("Loan not found for book with id %s".formatted(bookToReturn.getId()));
        }
        bookToReturn.setLoan(null);
        bookToReturn.setAvailable(true);
        bookService.updateBook(bookToReturn);
        loanRepository.deleteById(loan.getId());
    }

    public Loan findLoanById(long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("No loan found with the id: %d".formatted(id)));
    }

    private void updateBookToLoan(Book bookToLoan, Loan loan) {
        bookToLoan.setLoan(loan);
        bookToLoan.setAvailable(false);
        bookService.updateBook(bookToLoan);
    }
}
