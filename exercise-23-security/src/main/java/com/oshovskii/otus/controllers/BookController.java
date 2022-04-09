package com.oshovskii.otus.controllers;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.AuthorService;
import com.oshovskii.otus.services.BookService;
import com.oshovskii.otus.services.ConversionService;
import com.oshovskii.otus.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final ConversionService conversionService;


    @GetMapping({"/", "/books"})
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        List<BookDto> booksDto = conversionService.fromDomain(books);

        model.addAttribute("booksDto", booksDto);

        return "books";
    }

    @GetMapping("/books/editBook")
    public String editBook(@RequestParam("id") long id, Model model) {
        Book book = bookService.findById(id);
        BookDto bookDto = conversionService.fromDomain(book);

        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();

        model.addAttribute("bookDto", bookDto);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);

        return "editBook";
    }

    @Validated
    @PostMapping("/books/editBook")
    public String saveBook(@Valid @ModelAttribute("bookDto") BookDto bookDto,
                           BindingResult bindingResult,
                           Model model) {


        if (bindingResult.hasErrors()) {
            List<Author> authors = authorService.findAll();
            List<Genre> genres = genreService.findAll();

            model.addAttribute("bookDto", bookDto);
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);

            return "editBook";
        }

        bookService.saveBook(conversionService.fromDto(bookDto));
        return "redirect:/books";
    }

    @PostMapping("/books/delete")
    public String delete(@RequestParam("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
