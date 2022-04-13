package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.AuthorDto;
import com.oshovskii.otus.services.interfaces.AuthorService;
import com.oshovskii.otus.shell.interfaces.ShellAuthor;
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
public class ShellAuthorImpl implements ShellAuthor {
    private final ShellLoginImpl shellLogin;
    private final AuthorService authorService;
    private final ModelMapper modelMapper;

    @Override
    @ShellMethod(value = "Publish all authors", key = {"allAuthors", "allA"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public List<AuthorDto> publishAllAuthors() {
        return authorService.findAllAuthors()
                .stream()
                .map(author -> modelMapper.map(author, AuthorDto.class))
                .collect(Collectors.toList());
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
