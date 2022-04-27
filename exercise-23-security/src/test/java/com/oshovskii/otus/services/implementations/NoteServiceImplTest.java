package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.models.Note;
import com.oshovskii.otus.repositories.NoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Note repository with service testing.")
@DataJpaTest
class NoteServiceImplTest {

    private final static long NOTE_ONE_ID = 1L;
    private final static String NOTE_ONE_CONTEXT = "Note-1.1 - A";
    private final static String NOTE_ONE_CONTEXT_NEW = "Note-1.1 - A - New - TEST";
    private final static Author AUTHOR_ONE = new Author(1, "Dan Brown");
    private final static Genre GENRE_ONE = new Genre(1, "Roman");
    private final static String BOOK_ONE_NAME = "THE DA VINCI CODE";
    private final static Book BOOK_ONE = new Book(1, AUTHOR_ONE, GENRE_ONE, BOOK_ONE_NAME);

    @Autowired
    private NoteRepository noteRepository;

    @DisplayName("Should be able to insert a Genre-1 after deletions test")
    @Test
    void shouldAddNewGenre() {
        Note note = new Note(0L, BOOK_ONE, NOTE_ONE_CONTEXT_NEW);
        Note savedNote = noteRepository.save(note);

        assertThat(savedNote.getId()).isPositive();
        assertEquals(NOTE_ONE_CONTEXT_NEW, savedNote.getNote());
    }

    @DisplayName("Should get correct Note by ID test")
    @Test
    void shouldGetCorrectGenre() {
        Note genre = noteRepository.getById(NOTE_ONE_ID);

        assertEquals(NOTE_ONE_ID, genre.getId());
        assertEquals(NOTE_ONE_CONTEXT, genre.getNote());
    }
}
