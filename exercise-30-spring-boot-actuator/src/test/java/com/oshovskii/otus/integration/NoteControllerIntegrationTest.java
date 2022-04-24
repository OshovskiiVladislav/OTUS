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

import static com.oshovskii.otus.factory.NoteDtoFactory.createNoteDto;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles({"integration"})
@DisplayName("NoteController integration test")
class NoteControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @Test
    void create_validNoteDto_shouldCreateNoteAndReturnNote() throws Exception {
        // Config
        val expectedNoteName = "test";

        val sourceNoteDto = createNoteDto(1);
        sourceNoteDto.setNote(expectedNoteName);

        val sourceJson = objectMapper.writeValueAsString(sourceNoteDto);

        // Call and Verify
        mockMvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.note", is(expectedNoteName)));
    }

    @Test
    void delete_validNoteId_shouldDeleteNoteAndVoidOutput() throws Exception {
        // Config
        val noteId = 1L;

        // Call and Verify
        mockMvc.perform(delete("/api/v1/notes/{id}", noteId))
                .andExpect(status().isOk());
    }

    @Test
    void findByBookId_validBookId_shouldReturnListNoteDto() throws Exception {
        // Config
        val bookId = 1L;

        // Call and Verify
        mockMvc.perform(get("/api/v1/notes/{bookId}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }
}
