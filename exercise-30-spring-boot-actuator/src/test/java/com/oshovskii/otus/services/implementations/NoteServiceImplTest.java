package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.repositories.NoteRepository;
import com.oshovskii.otus.services.NoteService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.oshovskii.otus.factory.NoteDtoFactory.createNoteDto;
import static com.oshovskii.otus.factory.NoteFactory.createNote;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("NoteService test")
@ExtendWith(SpringExtension.class)
@Import(NoteServiceImpl.class)
class NoteServiceImplTest {

    @SpyBean
    private NoteService noteService;

    @MockBean
    private NoteRepository noteRepository;

    @Test
    void findByBookId_validBookId_shouldReturnListNotes() {
        //Config
        val bookId = 1L;
        val targetNote1 = createNote(1);
        val targetNote2 = createNote(2);

        val targetNoteList = List.of(targetNote1, targetNote2);

        when(noteRepository.findByBookId(bookId)).thenReturn(targetNoteList);

        // Call
        val actualListNotes = noteService.findByBookId(bookId);

        // Verify
        assertEquals(targetNoteList, actualListNotes);
    }

    @Test
    void save_validNote_shouldSaveNoteAndReturnNote() {
        //Config
        val sourceNote = createNote(1);
        val targetNote = createNote(2);

        when(noteRepository.save(sourceNote)).thenReturn(targetNote);

        // Call
        val actualNote = noteService.save(sourceNote);

        // Verify
        assertEquals(targetNote, actualNote);
    }

    @Test
    void deleteById_validNoteId_shouldDeleteNoteAndVoidOutput() {
        //Config
        val noteId = 1L;

        doNothing().when(noteRepository).deleteById(noteId);

        // Call
        noteService.deleteById(noteId);

        // Verify
        verify(noteService).deleteById(noteId);
    }
}