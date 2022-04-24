package com.oshovskii.otus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.services.AuthorService;
import lombok.val;
import net.bytebuddy.agent.builder.AgentBuilder;
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

@DisplayName("AuthorController test")
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    @Test
    void finAll_voidInput_shouldReturnAllAuthors() throws Exception {
        // Config
        val author = new Author();
        val expectedAuthorList = List.of(author);

        when(authorService.findAll()).thenReturn(expectedAuthorList);

        val targetJson = objectMapper.writeValueAsString(expectedAuthorList);

        // Call and Verify
        mvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(content().string(targetJson));
    }
}