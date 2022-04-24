package com.oshovskii.otus.controllers;

import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AuthorController {

    private final AuthorService service;

    @GetMapping("/authors")
    public List<Author> finAll() {
        return service.findAll();
    }

}
