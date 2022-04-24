package com.oshovskii.otus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.services.GenreService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("GenreController test")
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenreService genreService;

    @Test
    void finAll_voidInput_shouldReturnListGenres() throws Exception {
        // Config
        val genre = new Genre();
        val expectedGenresList = List.of(genre);

        when(genreService.findAll()).thenReturn(expectedGenresList);

        val targetJson = objectMapper.writeValueAsString(expectedGenresList);

        // Call and Verify
        mvc.perform(get("/api/v1/genres"))
                .andExpect(status().isOk())
                .andExpect(content().string(targetJson));
    }
}