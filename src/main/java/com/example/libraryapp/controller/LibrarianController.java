package com.example.libraryapp.controller;

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
    private static final String REDIRECT_TO_ALL_BOOKS = "redirect:/librarian/books";

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

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "librarian/books";
    }

    @GetMapping("new")
    public String showNewBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "librarian/new-book";
    }

    @PostMapping("books")
    public String addNewBook(Book book) {
        book.setAvailable(true);
        bookService.addNewBook(book);
        return REDIRECT_TO_ALL_BOOKS;
    }

    @GetMapping("/books/{id}")
    public String viewOneBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "librarian/book-details";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return REDIRECT_TO_ALL_BOOKS;
    }

    @GetMapping("/books/edition-form/{id}")
    public String getBookEditionForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "librarian/book-edition";
    }

    @PostMapping("/books/edition/{id}")
    public String bookPutMethod(@PathVariable Long id, @ModelAttribute Book book) {
        bookService.updateBook(book);
        return REDIRECT_TO_ALL_BOOKS;
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
    public String registerBookLoan(@RequestParam("book.barcode") Integer barcode,
                                   @RequestParam("libraryCard.number") int libraryCardNumber) {
        Book bookToLoan = bookService.findBookByBarcode(barcode);
        LibraryCard libraryCard = libraryCardService.findLibraryCardByCardNumber(libraryCardNumber);

        Loan loan = new Loan();
        loan.setBook(bookToLoan);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusMonths(3));
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



        return REDIRECT_TO_MAIN_VIEW;
    }

    @GetMapping("return")
    public String getReturnForm(Model model) {
        model.addAttribute("book", new Book());
        return "librarian/book-return";
    }

    @GetMapping("return/register")
    public String registerBookReturn(@RequestParam("barcode") Integer barcode) {
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
