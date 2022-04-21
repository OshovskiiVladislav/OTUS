package com.oshovskii.otus.factory;

import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Note;

public class NoteFactory {
    public static Note createNote(int index){
        return new Note(
                (long) index,
                new Book(),
                "note_" + index
        );
    }
}
