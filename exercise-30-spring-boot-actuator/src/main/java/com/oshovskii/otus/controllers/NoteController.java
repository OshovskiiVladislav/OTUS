package com.oshovskii.otus.controllers;

import com.oshovskii.otus.dto.NoteDto;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Note;
import com.oshovskii.otus.services.BookService;
import com.oshovskii.otus.services.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Slf4j
public class NoteController {

    private final BookService bookService;
    private final NoteService noteService;
    private final ModelMapper mapper;

    @ResponseStatus(CREATED)
    @PostMapping("/notes")
    public Note create(@RequestBody NoteDto dto) {
        log.info("Create. ");
        Book book = bookService.findById(dto.getBookId());
        Note note = new Note(0L, book, dto.getNote());
        return noteService.save(note);
    }

    @GetMapping("/notes/{bookId}")
    public List<NoteDto> findByBookId(@PathVariable("bookId") Long bookId) {
        return noteService.findByBookId(bookId)
                .stream()
                .map(note -> mapper.map(note, NoteDto.class))
                .collect(toList());
    }

    @DeleteMapping("/notes/{id}")
    public void delete(@PathVariable("id") Long id) {
        noteService.deleteById(id);
    }
}
