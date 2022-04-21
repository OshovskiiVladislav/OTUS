package com.oshovskii.otus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.otus.dto.NoteDto;
import com.oshovskii.otus.models.Note;
import com.oshovskii.otus.services.BookService;
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

import static com.oshovskii.otus.factory.BookFactory.createBook;
import static com.oshovskii.otus.factory.NoteDtoFactory.createNoteDto;
import static com.oshovskii.otus.factory.NoteFactory.createNote;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("NoteController test")
@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteService noteService;

    @MockBean
    private BookService bookService;

    @MockBean
    private ModelMapper mapper;

    @Test
    void create_validNoteDto_shouldCreateNoteAndReturnNote() throws Exception {
        // Config
        val bookId = 1L;
        val sourceBook = createBook(1);
        val targetNote = createNote(1);

        when(bookService.findById(bookId)).thenReturn(sourceBook);
        when(noteService.save(any(Note.class))).thenReturn(targetNote);

        val sourceJson = objectMapper.writeValueAsString(sourceBook);

        // Call and Verify
        mvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(status().isCreated());
    }

    @Test
    void delete_validNoteId_shouldDeleteNoteAndVoidOutput() throws Exception {
        // Config
        val noteId = 1L;

        doNothing().when(noteService).deleteById(noteId);

        // Call and Verify
        mvc.perform(delete("/api/v1/notes/{id}", noteId))
                .andExpect(status().isOk());
    }

    @Test
    void findByBookId_validBookId_shouldReturnListNoteDto() throws Exception {
        // Config
        val bookId = 1L;
        val targetNote1 = createNote(1);
        val targetNote2 = createNote(2);

        val targetNoteDto1 = createNoteDto(1);
        val targetNoteDto2 = createNoteDto(1);

        val targetNoteList = List.of(targetNote1, targetNote2);
        val targetNoteDtoList = List.of(targetNoteDto1, targetNoteDto2);

        when(noteService.findByBookId(bookId)).thenReturn(targetNoteList);
        when(mapper.map(targetNote1, NoteDto.class)).thenReturn(targetNoteDto1);
        when(mapper.map(targetNote2, NoteDto.class)).thenReturn(targetNoteDto2);

        val targetJson = objectMapper.writeValueAsString(targetNoteDtoList);

        // Call and Verify
        mvc.perform(get("/api/v1/notes/{bookId}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().string(targetJson));
    }
}
