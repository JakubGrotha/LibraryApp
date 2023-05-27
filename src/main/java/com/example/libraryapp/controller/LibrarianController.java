package com.example.libraryapp.controller;

import com.example.libraryapp.exception.BookAlreadyBorrowedException;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.LibraryCard;
import com.example.libraryapp.model.Loan;
import com.example.libraryapp.model.User;
import com.example.libraryapp.service.BookService;
import com.example.libraryapp.service.LibraryCardService;
import com.example.libraryapp.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("librarian")
public class LibrarianController {

    private static final String REDIRECT_TO_MAIN_VIEW = "redirect:/librarian";

    private final BookService bookService;
    private final LibraryCardService libraryCardService;
    private final LoanService loanService;

    public LibrarianController(BookService bookService,
                               LibraryCardService libraryCardService,
                               LoanService loanService) {
        this.bookService = bookService;
        this.libraryCardService = libraryCardService;
        this.loanService = loanService;
    }

    @GetMapping()
    public String getLibrarianMainMenu() {
        return "librarian/main-view";
    }

    @GetMapping("/return/check/")
    public String registerReturn(@RequestParam("isbn") String isbn) {
        if (bookService.checkIfIsbnHasCorrectFormat(isbn)) {
            return "redirect:/return/%s".formatted(isbn);
        } else {
            return REDIRECT_TO_MAIN_VIEW;
        }
    }

    @GetMapping("user/new")
    public String getNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "librarian/new-user";
    }

    @PostMapping("user")
    public String addNewUser(@ModelAttribute User user) {
        // add implementation
        return REDIRECT_TO_MAIN_VIEW;
    }

    @GetMapping("loan")
    public String getLoanForm() {
        return "librarian/book-loan";
    }

    @PostMapping("loan")
    public String registerBookLoan(@RequestParam("book.barcode") String barcode,
                                   @RequestParam("libraryCard.number") int libraryCardNumber,
                                   @RequestParam("duration") int duration) {
        Book bookToLoan = bookService.findBookByBarcode(barcode);
        LibraryCard libraryCard = libraryCardService.findLibraryCardByCardNumber(libraryCardNumber);
        if (bookToLoan.isAvailable()) {

            Loan loan = new Loan();
            loan.setBook(bookToLoan);
            loan.setLoanDate(LocalDate.now());
            loan.setReturnDate(LocalDate.now().plusMonths(duration));
            loan.setLibraryCard(libraryCard);

            List<Loan> currentLoans = libraryCard.getBookLoans();
            if (currentLoans == null) {
                currentLoans = new ArrayList<>();
            }
            currentLoans.add(loan);
            libraryCardService.updateLibraryCard(libraryCard);
            loanService.updateLoan(loan);

            bookToLoan.setLoan(loan);
            bookToLoan.setAvailable(false);
            bookService.updateBook(bookToLoan);
        } else {
            throw new BookAlreadyBorrowedException("Book with the following barcode has already been borrowed: %d"
                    .formatted(bookToLoan.getBarcode()));
        }


        return REDIRECT_TO_MAIN_VIEW;
    }

    @GetMapping("return")
    public String getReturnForm(Model model) {
        model.addAttribute("book", new Book());
        return "librarian/book-return";
    }

    @GetMapping("return/register")
    public String registerBookReturn(@RequestParam("barcode") String barcode) {
        Book bookToReturn = bookService.findBookByBarcode(barcode);

        if (bookToReturn != null) {
            Loan loan = bookToReturn.getLoan();
            bookToReturn.setLoan(null);
            bookToReturn.setAvailable(true);
            loanService.deleteLoanById(loan.getId());
        }

        return REDIRECT_TO_MAIN_VIEW;
    }
}
