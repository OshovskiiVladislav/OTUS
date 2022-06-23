package com.oshovskii.otus.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.oshovskii.otus.factory.TestBookDtoFactory.createBookDtoWithAllInfoById;
import static com.oshovskii.otus.utils.Utils.EXISTING_BOOK_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @DisplayName("Find all Book test")
    @Test
    void findAllBooks_expectedVoidInput_shouldReturnListBookDto() throws Exception {
        // Call and verify
        mockMvc.perform(get("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

    }

    @DisplayName("Find book by id test")
    @Test
    void findBookById_expectedValidBookId_shouldReturnBookDto() throws Exception {
        // Call and verify
        mockMvc.perform(get("/api/v1/books/", EXISTING_BOOK_ID))
                .andExpect(status().isOk());
    }

    @DisplayName("Save valid Book test")
    @Test
    void saveBook_expectedValidBookDto_shouldReturnBookDto() throws Exception {
        // Config
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);
        final String targetJson = objectMapper.writeValueAsString(expectedBookDto);

        // Call and verify
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(targetJson)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @DisplayName("Update valid Book test")
    @Test
    void updateBook_expectedValidBookDto_shouldReturnBookDto() throws Exception {
        // Config
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);
        final String targetJson = objectMapper.writeValueAsString(expectedBookDto);

        // Call and verify
        mockMvc.perform(put("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(targetJson)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @DisplayName("Delete valid Book vy id test")
    @Test
    void deleteBook_expectedValidBookId_shouldReturnVoid() throws Exception {
        // Call and verify
        mockMvc.perform(delete("/api/v1/books/" + EXISTING_BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}
