package com.oshovskii.otus.factory;

import com.oshovskii.otus.dto.NoteDto;

public class NoteDtoFactory {
    public static NoteDto createNoteDto(int index) {
        return new NoteDto(
                (long) index,
                "note_" + index,
                (long) index
        );
    }
}
