package com.oshovskii.otus.shell;

import com.oshovskii.otus.services.interfaces.GenreService;
import com.oshovskii.otus.shell.interfaces.ShellGenre;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class ShellGenreImpl implements ShellGenre  {
    private final ShellLoginImpl shellLogin;
    private final GenreService genreService;

    private Availability isPublishEventCommandAvailable() {
        return shellLogin.getCurrentUserName() == null
                ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }

    @Override
    @ShellMethod(value = "Save genre", key = {"saveGenre", "saveG", "sG"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String saveGenre(String type) {
        genreService.save(type);
        String completedCommandSaveGenre = "Save genre <"+ type + "> completed";
        return completedCommandSaveGenre;
    }
}
