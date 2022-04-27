package com.oshovskii.otus.controllers;


import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.models.Note;
import com.oshovskii.otus.services.BookService;
import com.oshovskii.otus.services.NoteService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("NotesController test")
@ExtendWith(SpringExtension.class)
@WithMockUser
@WebMvcTest(NotesController.class)
@MockBeans({@MockBean(UserDetailsService.class), @MockBean(BookService.class)})
class NotesControllerTest {

    private static final Author AUTHOR = new Author(1L, "Author1");
    private static final Genre GENRE = new Genre(1L, "Genre1");
    private static final Book BOOK = new Book(1L, AUTHOR, GENRE, "Book1");
    private static final Note NOTE = new Note(1L, BOOK, "Note1");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @WithMockUser
    @Test
    void save() throws Exception {
        given(noteService.save(any(Note.class))).willReturn(NOTE);

        this.mockMvc.perform(post("/notes/editBookNote")
                .with(csrf())
                .param("noteId", "1")
                .param("note", "Note1")
                .param("id", "1")
        ).andExpect(status().isFound());
    }

    @WithMockUser
    @Test
    void delete() throws Exception {
        doNothing().when(noteService).delete(anyLong());

        this.mockMvc.perform(post("/notes/delete")
                        .with(csrf())
                        .param("id", "1")
                        .param("bookId", "1")
                )
                .andExpect(status().isFound());
    }


    @Test
    void saveForbidden() throws Exception {
        given(noteService.save(any(Note.class))).willReturn(NOTE);
        this.mockMvc.perform(post("/notes/editBookNote")
                        .param("id", "1")
                        .param("note", "Note1")
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteForbidden() throws Exception {
        doNothing().when(noteService).delete(anyLong());

        this.mockMvc.perform(post("/notes/delete")
                        .param("id", "1")
                        .param("bookId", "1"))
                .andExpect(status().isForbidden());
    }

}