package com.oshovskii.otus.controllers;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.AuthorService;
import com.oshovskii.otus.services.BookService;
import com.oshovskii.otus.services.GenreService;
import com.oshovskii.otus.services.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Slf4j
public class BookController {

    private final BookService service;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/books")
    public List<Book> finAll() {
        return service.findAll();
    }

    @GetMapping("/books/{id}")
    public Book findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/books")
    public Book create(@RequestBody BookDto dto) {
        log.info("Create. ");
        Author author = authorService.findById(dto.getAuthorId());
        Genre genre = genreService.findById(dto.getGenreId());
        Book book = new Book(0L, author, genre, dto.getTitle());
        return service.save(book);
    }

    @PutMapping(value = "/books")
    public Book update(@RequestBody BookDto bookDto) {
        log.info("Update. ");
        Book book = service.findById(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(authorService.findById(bookDto.getAuthorId()));
        book.setGenre(genreService.findById(bookDto.getGenreId()));
        return service.save(book);
    }

    @DeleteMapping("/books/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
