package com.oshovskii.otus.controllers;

import com.oshovskii.otus.controllers.interfaces.AuthorController;
import com.oshovskii.otus.dto.AuthorDto;
import com.oshovskii.otus.services.interfaces.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorControllerImpl implements AuthorController {
    private final AuthorService authorService;

    @Override
    @GetMapping("/authors")
    public String listPageAuthors(Model model) {
        List<AuthorDto> authorDtoList = authorService.findAll();
        model.addAttribute("authors", authorDtoList);
        return "save";
    }
}
