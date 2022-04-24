package com.oshovskii.otus.services;

import com.oshovskii.otus.models.Note;

import java.util.List;

public interface NoteService {
    List<Note> findByBookId(long bookId);
    Note save(Note newNote);
    void deleteById(long id);
}
