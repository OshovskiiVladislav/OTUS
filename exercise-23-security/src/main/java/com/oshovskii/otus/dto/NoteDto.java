package com.oshovskii.otus.dto;

import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Note;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(exclude = "book")
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {

    private long id;

    @ToString.Exclude
    private Book book;

    @NotBlank
    @Size(min = 5, max = 100)
    private String note;


    public Note toDomainObject() {
        return new Note(this.id, this.book, this.note);
    }

    public static NoteDto fromDomainObject(Note note) {
        return new NoteDto(note.getId(), note.getBook(), note.getNote());
    }
}
