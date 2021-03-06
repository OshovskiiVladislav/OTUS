package com.oshovskii.otus.repositories;

import com.oshovskii.otus.models.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @EntityGraph(value = "note-book-author-genre")
    @Override
    List<Note> findAll();

    @EntityGraph(value = "note-book-author-genre")
    @Override
    Optional<Note> findById(Long id);

    @EntityGraph(value = "note-book-author-genre")
    List<Note> findByBookId(Long id);

    void deleteById(Long id);
}