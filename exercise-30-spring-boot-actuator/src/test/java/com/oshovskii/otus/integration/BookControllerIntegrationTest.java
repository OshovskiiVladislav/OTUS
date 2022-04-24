package com.oshovskii.otus.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.oshovskii.otus.factory.BookDtoFactory.createBookDto;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles({"integration"})
@DisplayName("BookController integration test")
class BookControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void finAll_voidInput_shouldReturnAllBooks() throws Exception {
        // Config
        val bookTitle = "THE DA VINCI CODE";

        // Call and Verify
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$[0].title", is(bookTitle)));;
    }

    @Test
    void findById_validBookId_shouldReturnExpectedBook() throws Exception {
        // Config
        val bookId = 1L;
        val bookTitle = "THE DA VINCI CODE";

        // Call and Verify
        mockMvc.perform(get("/api/v1/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(bookTitle)));
    }

    @Test
    void create_validBookDto_shouldSaveBookAndReturnBook() throws Exception {
        // Config
        val bookDto = createBookDto(1);
        val sourceJson = objectMapper.writeValueAsString(bookDto);

        // Call and Verify
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(bookDto.getTitle())));
    }

    @Test
    void update_validBookDto_shouldUpdateBookAndReturnBook() throws Exception {
        // Config
        val bookDto = createBookDto(4);
        bookDto.setId(4L);

        val sourceJson = objectMapper.writeValueAsString(bookDto);

        // Call and Verify
        mockMvc.perform(put("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(bookDto.getTitle())));
    }

    @Test
    void delete_voidInput_shouldDeleteBook() throws Exception {
        // Config
        val bookId = 8L;

        // Call and Verify
        mockMvc.perform(delete("/api/v1/books/{id}", bookId))
                .andExpect(status().isOk());
    }
}
