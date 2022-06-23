package com.oshovskii.otus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.otus.services.interfaces.BookService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.oshovskii.otus.factory.TestBookDtoFactory.createBookDtoWithAllInfoById;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@SpringJUnitWebConfig(classes = BookControllerImpl.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    public static final Long EXISTING_BOOK_ID = 1L;
    public static final Long EXISTING_BOOK_ID_2 = 2L;

    @DisplayName("Find all Book test")
    @Test
    void findAllBooks_expectedVoidInput_shouldReturnListBookDto() throws Exception {
        // Config
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);
        val expectedBookDto2 = createBookDtoWithAllInfoById(EXISTING_BOOK_ID_2);

        val bookDtoList = List.of(expectedBookDto, expectedBookDto2);

        when(bookService.findAllBooks()).thenReturn(bookDtoList);

        val targetJson = objectMapper.writeValueAsString(bookDtoList);

        // Call and verify
        mockMvc.perform(get("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());

    }

    @DisplayName("Find book by id test")
    @Test
    void findBookById_expectedValidBookId_shouldReturnBookDto() throws Exception {
        // Config
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        when(bookService.findBookById(EXISTING_BOOK_ID)).thenReturn(expectedBookDto);


        // Call and verify
        mockMvc.perform(get("/api/v1/books/", EXISTING_BOOK_ID))
                .andExpect(status().isOk());
    }

    @DisplayName("Save valid book test")
    @Test
    void saveBook_expectedValidBookDto_shouldReturnBookDto() throws Exception {
        // Config
        val sourceBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        when(bookService.saveBook(sourceBookDto)).thenReturn(expectedBookDto);

        val sourceJson = objectMapper.writeValueAsString(sourceBookDto);

        // Call and verify
        mockMvc.perform(post("/api/v1/books")
                        .content(sourceJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Update valid Book test")
    @Test
    void updateBook_expectedValidBookDto_shouldReturnBookDto() throws Exception {
        // Config
        val sourceBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        when(bookService.saveBook(sourceBookDto)).thenReturn(expectedBookDto);

        val sourceJson = objectMapper.writeValueAsString(sourceBookDto);

        // Call and verify
        mockMvc.perform(put("/api/v1/books/book")
                        .content(sourceJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Delete valid Book vy id test")
    @Test
    void deleteBook_expectedValidBookId_shouldReturnVoid() throws Exception {
        // Config
        doNothing().when(bookService).deleteBookById(EXISTING_BOOK_ID);

        // Call and verify
        mockMvc.perform(delete("/api/v1/books/" + EXISTING_BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBookById(EXISTING_BOOK_ID);
    }
}
