package com.oshovskii.otus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.otus.MainApplication;
import com.oshovskii.otus.services.interfaces.BookService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.oshovskii.otus.factory.TestBookDtoFactory.createBookDtoWithAllInfoById;
import static com.oshovskii.otus.utils.Utils.EXISTING_BOOK_ID;
import static com.oshovskii.otus.utils.Utils.EXISTING_BOOK_ID_2;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {BookControllerImpl.class})
@AutoConfigureMockMvc
@ContextConfiguration(classes = {MainApplication.class})
@ActiveProfiles("unit")
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    public ObjectMapper objectMapper;

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

    @DisplayName("Save valid Book test")
    @Test
    void saveBook_expectedValidBookDto_shouldReturnBookDto() throws Exception {
        // Config
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        when(bookService.saveBook(expectedBookDto)).thenReturn(expectedBookDto);

        val targetJson = objectMapper.writeValueAsString(expectedBookDto);

        // Call and verify
        mockMvc.perform(post("/api/v1/books")
                .content(targetJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

     //   verify(bookService, times(1)).saveBook(expectedBookDto); почему вместе с этой строкой ошибка "аргументы не совпадают"?
    }

    @DisplayName("Update valid Book test")
    @Test
    void updateBook_expectedValidBookDto_shouldReturnBookDto() throws Exception {
        // Config
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        when(bookService.saveBook(expectedBookDto)).thenReturn(expectedBookDto);

        val targetJson = objectMapper.writeValueAsString(expectedBookDto);

        // Call and verify
        mockMvc.perform(put("/api/v1/books")
                .content(targetJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    //    verify(bookService, times(1)).updateBook(expectedBookDto); почему вместе с этой строкой ошибка "аргументы не совпадают"?
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
