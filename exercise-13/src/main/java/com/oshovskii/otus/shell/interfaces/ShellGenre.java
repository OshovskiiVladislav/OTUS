package com.oshovskii.otus.shell.interfaces;

import com.oshovskii.otus.dto.GenreDto;

import java.util.List;

public interface ShellGenre {
    List<GenreDto> publishAllGenres();
    String saveGenre(String type);
}
