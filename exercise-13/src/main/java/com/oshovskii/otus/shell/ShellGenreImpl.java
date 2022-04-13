package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.GenreDto;
import com.oshovskii.otus.services.interfaces.GenreService;
import com.oshovskii.otus.shell.interfaces.ShellGenre;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ShellGenreImpl implements ShellGenre  {
    private final ShellLoginImpl shellLogin;
    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @Override
    @ShellMethod(value = "Publish all genres", key = {"allGenres", "allG"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public List<GenreDto> publishAllGenres() {
        return genreService.findAllGenres()
                .stream()
                .map(genre -> modelMapper.map(genre, GenreDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @ShellMethod(value = "Save genre", key = {"saveGenre", "saveG", "sG"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String saveGenre(String type) {
        genreService.save(type);
        String completedCommandSaveGenre = "Save genre <"+ type + "> completed";
        return completedCommandSaveGenre;
    }

    private Availability isPublishEventCommandAvailable() {
        return shellLogin.getCurrentUserName() == null
                ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}
