package com.oshovskii.otus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.dto.BookToSaveDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.BookService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.oshovskii.otus.factory.dto.TestDtoFactory.createBookDto;
import static com.oshovskii.otus.factory.entity.TestEntityFactory.createBook;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class, useDefaultFilters = false)
@WebMvcTest
@SpringJUnitWebConfig(classes = BookController.class)
@DisplayName("BookController test")
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookServiceMock;

    @MockBean
    private ModelMapper modelMapperMock;

    @Test
    @DisplayName("findAll() " +
            "with void input " +
            "should return all books test")
    @WithMockUser(username="admin",roles={"EDITOR"})
    void findAll_voidInput_shouldReturnAllBooks() throws Exception {
        // Config
        val sourceBook1 = createBook(1);
        val sourceBook2 = createBook(2);

        val sourceListBook = List.of(sourceBook1, sourceBook2);

        val expectedBookDto1 = createBookDto(3);
        val expectedBookDto2 = createBookDto(4);

        val expectedBookDtoList = List.of(expectedBookDto1, expectedBookDto2);

        when(bookServiceMock.findAll())
                .thenReturn(sourceListBook);

        when(modelMapperMock.map(sourceBook1, BookDto.class))
                .thenReturn(expectedBookDto1);

        when(modelMapperMock.map(sourceBook2, BookDto.class))
                .thenReturn(expectedBookDto2);

        val targetJson = objectMapper.writeValueAsString(expectedBookDtoList);

        // Call and Verify
        mvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().string(targetJson));
    }

    @Test
    @DisplayName("findById() " +
            "with valid book id " +
            "should return expected book test")
    @WithMockUser(username="admin",roles={"EDITOR"})
    void findById_validBookId_shouldReturnExpectedBook() throws Exception {
        // Config
        val bookId = 1L;
        val expectedBook = createBook(1);
        val expectedBookDto = createBookDto(2);

        when(bookServiceMock.findById(bookId))
                .thenReturn(expectedBook);

        when(modelMapperMock.map(expectedBook, BookDto.class))
                .thenReturn(expectedBookDto);

        val targetJson = objectMapper.writeValueAsString(expectedBookDto);

        // Call and Verify
        mvc.perform(get("/api/v1/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().string(targetJson));
    }

    @Test
    @DisplayName("save() " +
            "with valid BookToSaveDto " +
            "should return saved book test")
    @WithMockUser(username="admin",roles={"EDITOR"})
    void save_validBookToSaveDto_shouldReturnSavedBook() throws Exception {
        // Config
        val savedBook = createBook(1);
        val expectedBookDto = createBookDto(1);

        val sourceBookToSaveDto = new BookToSaveDto(
                "title",
                "author_name",
                "genre_name"
        );

        when(bookServiceMock.save(sourceBookToSaveDto))
                .thenReturn(savedBook);

        when(modelMapperMock.map(savedBook, BookDto.class)).thenReturn(expectedBookDto);

        val sourceJson = objectMapper.writeValueAsString(expectedBookDto);

        // Call and Verify
        mvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(status().isCreated());
    }
}
