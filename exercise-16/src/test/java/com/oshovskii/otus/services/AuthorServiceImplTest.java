package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.AuthorDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.repositories.AuthorRepository;
import com.oshovskii.otus.services.interfaces.AuthorService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("AuthorServiceImpl Test")
@ExtendWith(SpringExtension.class)
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @SpyBean
    private AuthorService authorService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Return expected list authors test")
    void findAll_voidInput_shouldReturnAllAuthors() {
        // Config
        val authorId1 = 1L;
        val authorId2 = 2L;

        val authorName1 = "John";
        val authorName2 = "Anna";

        val expectedAuthor1 = new Author(authorId1, authorName1);
        val expectedAuthor2 = new Author(authorId2, authorName2);

        val expectedAuthorDto1 = new AuthorDto(authorId1, authorName1);
        val expectedAuthorDto2 = new AuthorDto(authorId2, authorName2);

        val expectedAuthorList = List.of(expectedAuthor1, expectedAuthor2);
        val expectedAuthorDtoList = List.of(expectedAuthorDto1, expectedAuthorDto2);

        when(authorRepository.findAll()).thenReturn(expectedAuthorList);
        when(modelMapper.map(expectedAuthor1, AuthorDto.class)).thenReturn(expectedAuthorDto1);
        when(modelMapper.map(expectedAuthor2, AuthorDto.class)).thenReturn(expectedAuthorDto2);

        // Call
        val actualAuthorDtoList = authorService.findAll();

        // Verify
        assertEquals(expectedAuthorDtoList, actualAuthorDtoList);
    }
}