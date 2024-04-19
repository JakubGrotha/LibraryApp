package com.example.libraryapp.controller;

import com.example.libraryapp.exception.BookDetailsNotFoundException;
import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.service.BookDetailsService;
import com.example.libraryapp.service.GoogleBooksApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("librarian")
@RequiredArgsConstructor
public class LibrarianBookDetailsController {

    private final BookDetailsService bookDetailsService;
    private final GoogleBooksApiService googleBooksApiService;

    @GetMapping("/book-details")
    public String getBookInformation(Model model) {
        model.addAttribute("bookDetails", bookDetailsService.getAllBookDetails());
        return "librarian/book-details";
    }

    @GetMapping("/book-details/{id}")
    public String getBookDetailsOfASingleBook(@PathVariable("id") long bookDetailsId, Model model) {
        model.addAttribute("bookDetails", bookDetailsService.findBookDetailsById(bookDetailsId));
        return "librarian/single-book-details";
    }

    @GetMapping("/isbn-search")
    public String getIsbnForm() {
        return "librarian/isbn-form";
    }

    @GetMapping("/new-book")
    public String getNewBookDetailsForm(Model model, @RequestParam("book.isbn") String isbn) {
        isbn = isbn.replace("-", "");
        try {
            BookDetails bookDetails = bookDetailsService.findBookDetailsByIsbn(isbn);
            return "redirect:/librarian/new?id=%d".formatted(bookDetails.getId());
        } catch (BookDetailsNotFoundException e) {
            BookDetails googleBooksBasedBookDetails = googleBooksApiService
                    .findBookDetailsInGoogleBooksApiUsingIsbn(isbn);
            googleBooksBasedBookDetails.setIsbn(isbn);
            model.addAttribute("bookDetails", googleBooksBasedBookDetails);
        }
        return "librarian/new-book-details";
    }

    @PostMapping("/book-details")
    public String addNewBookDetails(@ModelAttribute BookDetails bookDetails) {
        bookDetailsService.addNewBookDetails(bookDetails);
        return "redirect:/librarian/new?id=%d".formatted(bookDetails.getId());
    }

    @GetMapping("/book-details/edition-form/{id}")
    public String getBookEditionForm(@PathVariable long id, Model model) {
        model.addAttribute("bookDetails", bookDetailsService.findBookDetailsById(id));
        return "librarian/book-details-edition";
    }

    @PostMapping("/book-details/edition/{id}")
    public String bookPutMethod(@PathVariable("id") long id, @ModelAttribute BookDetails bookDetails) {
        bookDetailsService.updateBookDetails(bookDetails);
        return "redirect:/librarian/book-details";
    }
}
