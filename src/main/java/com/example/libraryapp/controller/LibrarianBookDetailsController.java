package com.example.libraryapp.controller;

import com.example.libraryapp.model.BookDetails;
import com.example.libraryapp.service.BookDetailsService;
import com.example.libraryapp.service.GoogleBooksApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.libraryapp.service.BookDetailsService.LookupResult;

@Controller
@RequestMapping("/librarian")
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
        String pureIsbn = isbn.replace("-", "");
        LookupResult lookupResult = bookDetailsService.findBookDetailsByIsbn(pureIsbn);
        return switch (lookupResult) {
            case LookupResult.FoundInDatabase(var bookDetails) ->
                    "redirect:/librarian/new?id=%d".formatted(bookDetails.getId());
            case LookupResult.FoundInGoogleBooks(var bookDetails) -> {
                model.addAttribute("bookDetails", bookDetails);
                yield "librarian/new-book-details";
            }
            case LookupResult.NotFound _ -> {
                model.addAttribute(("bookDetails"), BookDetails.builder().isbn(pureIsbn).build());
                yield "librarian/new-book-details";
            }
        };
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
