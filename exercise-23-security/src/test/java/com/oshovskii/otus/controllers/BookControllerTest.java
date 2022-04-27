package com.oshovskii.otus.controllers;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.AuthorService;
import com.oshovskii.otus.services.BookService;
import com.oshovskii.otus.services.ConversionService;
import com.oshovskii.otus.services.GenreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("BookController test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@MockBeans({@MockBean(UserDetailsService.class), @MockBean(AuthorService.class), @MockBean(GenreService.class)})
class BookControllerTest {
    private static final Author AUTHOR = new Author(1L, "AuthorL");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book BOOK = new Book(1L, AUTHOR, GENRE, "Title1");
    private static final BookDto BOOK_DTO = new BookDto(1L, AUTHOR, GENRE, "Title1", 0);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private ConversionService conversionService;

    @WithMockUser
    @Test
    void finAll() throws Exception {
        given(bookService.findAll()).willReturn(List.of(BOOK));

        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    void edit() throws Exception {
        given(bookService.findById(anyLong())).willReturn(BOOK);
        given(conversionService.fromDomain(BOOK)).willReturn(BOOK_DTO);

        this.mockMvc.perform(get("/books/editBook?id=" + BOOK
                        .getId())).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void save() throws Exception {
        given(bookService.saveBook(any(Book.class))).willReturn(BOOK);

        this.mockMvc.perform(post("/books/editBook")
                        .with(csrf())
                        .param("id", "1")
                        .param("title", "Title1"))
                .andExpect(status().isFound());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(bookService).deleteBook(anyLong());

        this.mockMvc.perform(post("/books/delete")
                        .with(csrf())
                        .param("id", "1"))
                .andExpect(status().isFound());
    }

    @Test
    void finAllForbidden() throws Exception {
        given(bookService.findAll()).willReturn(List.of(BOOK));

        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isFound());
    }

    @Test
    void editForbidden() throws Exception {
        given(bookService.findById(anyLong())).willReturn(BOOK);
        this.mockMvc.perform(get("/books/edit?id=" + BOOK.getId())).andDo(print()).andExpect(status().isFound());
    }

    @Test
    void saveForbidden() throws Exception {
        given(bookService.saveBook(any(Book.class))).willReturn(BOOK);

        this.mockMvc.perform(post("/books/edit")
                        .param("id", "1")
                        .param("name", "book1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteForbidden() throws Exception {
        doNothing().when(bookService).deleteBook(anyLong());

        this.mockMvc.perform(post("/books/delete")
                        .param("id", "1"))
                .andExpect(status().isForbidden());
    }
}
