package com.oshovskii.otus.shell;

import com.oshovskii.otus.services.interfaces.AuthorService;
import com.oshovskii.otus.shell.interfaces.ShellAuthor;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class ShellAuthorImpl implements ShellAuthor {
    private final ShellLoginImpl shellLogin;
    private final AuthorService authorService;

    @Override
    @ShellMethod(value = "Publish all authors", key = {"allAuthors", "allA"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishAllAuthors() {
        return authorService.findAllAuthors().toString();
    }

    @Override
    @ShellMethod(value = "Save author", key = {"saveAuthor", "saveA", "sA"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String saveAuthor(String name) {
        authorService.save(name);
        String completedCommandSaveAuthor = "Save author <" + name + "> completed";
        return completedCommandSaveAuthor;
    }

    private Availability isPublishEventCommandAvailable() {
        return shellLogin.getCurrentUserName() == null
                ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}
