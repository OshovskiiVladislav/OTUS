package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.otus.models.Note;
import com.oshovskii.otus.repositories.NoteRepository;
import com.oshovskii.otus.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private static final String NOTE_NOT_EXIST = "Wasn't able to find note with this ID.";

    @Override
    public List<Note> findByBookId(long bookId) {
        return noteRepository.findByBookId(bookId);
    }

    @Transactional
    @Override
    public Note save(Note note) {
        if (note == null) {
            throw new ResourceNotFoundException(NOTE_NOT_EXIST);
        }
        return noteRepository.save(note);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        noteRepository.deleteById(id);
    }
}
