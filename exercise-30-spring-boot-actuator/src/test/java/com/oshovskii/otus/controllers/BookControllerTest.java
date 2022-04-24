package com.oshovskii.otus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.AuthorService;
import com.oshovskii.otus.services.BookService;
import com.oshovskii.otus.services.GenreService;
import com.oshovskii.otus.services.NoteService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.oshovskii.otus.factory.BookDtoFactory.createBookDto;
import static com.oshovskii.otus.factory.BookFactory.createBook;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("BookController test")
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Test
    void finAll_voidInput_shouldReturnAllBooks() throws Exception {
        // Config
        val book1 = createBook(1);
        val book2 = createBook(2);

        val expectedBookList = List.of(book1, book2);

        when(bookService.findAll()).thenReturn(expectedBookList);

        val targetJson = objectMapper.writeValueAsString(expectedBookList);

        // Call and Verify
        mvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(targetJson));
    }

    @Test
    void findById_validBookId_shouldReturnExpectedBook() throws Exception {
        // Config
        val bookId = 1L;
        val book = createBook(1);

        when(bookService.findById(bookId)).thenReturn(book);

        val targetJson = objectMapper.writeValueAsString(book);

        // Call and Verify
        mvc.perform(get("/api/v1/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().string(targetJson));
    }

    @Test
    void create_validBookDto_shouldSaveBookAndReturnBook() throws Exception {
        // Config
        val book = createBook(1);
        val bookDto = createBookDto(1);

        when(bookService.save(any(Book.class))).thenReturn(book);
        when(authorService.findById(bookDto.getAuthorId())).thenReturn(new Author());
        when(genreService.findById(bookDto.getGenreId())).thenReturn(new Genre());

        val sourceJson = objectMapper.writeValueAsString(bookDto);

        // Call and Verify
        mvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(status().isCreated());
    }

    @Test
    void update_validBookDto_shouldUpdateBookAndReturnBook() throws Exception {
        // Config
        val book = createBook(1);
        val bookDto = createBookDto(1);

        when(bookService.findById(bookDto.getId())).thenReturn(book);
        when(authorService.findById(bookDto.getAuthorId())).thenReturn(new Author());
        when(genreService.findById(bookDto.getGenreId())).thenReturn(new Genre());
        when(bookService.save(any(Book.class))).thenReturn(book);

        val sourceJson = objectMapper.writeValueAsString(bookDto);

        // Call and Verify
        mvc.perform(put("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(status().isOk());
    }

    @Test
    void delete_voidInput_shouldDeleteBook() throws Exception {
        // Config
        val bookId = 1L;

        doNothing().when(bookService).deleteById(bookId);

        // Call and Verify
        mvc.perform(delete("/api/v1/books/{id}", bookId))
                .andExpect(status().isOk());
    }
}
