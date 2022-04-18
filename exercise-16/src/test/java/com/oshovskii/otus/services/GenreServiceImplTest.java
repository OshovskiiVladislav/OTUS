package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.GenreDto;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.GenreRepository;
import com.oshovskii.otus.services.interfaces.GenreService;
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

@DisplayName("GenreServiceImpl Test")
@ExtendWith(SpringExtension.class)
@Import(GenreServiceImpl.class)
class GenreServiceImplTest {

    @SpyBean
    private GenreService genreService;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    @DisplayName("Return expected list genres test")
    void findAll() {
        // Config
        val genreId1 = 1L;
        val genreId2 = 2L;

        val genreType1 = "Detective";
        val genreType2 = "Fantasy";

        val expectedGenre1 = new Genre(genreId1, genreType1);
        val expectedGenre2 = new Genre(genreId2, genreType2);

        val expectedGenreDto1 = new GenreDto(genreId1, genreType1);
        val expectedGenreDto2 = new GenreDto(genreId2, genreType2);

        val expectedGenreList = List.of(expectedGenre1, expectedGenre2);
        val expectedGenreDtoList = List.of(expectedGenreDto1, expectedGenreDto2);

        when(genreRepository.findAll()).thenReturn(expectedGenreList);
        when(modelMapper.map(expectedGenre1, GenreDto.class)).thenReturn(expectedGenreDto1);
        when(modelMapper.map(expectedGenre2, GenreDto.class)).thenReturn(expectedGenreDto2);

        // Call
        val actualGenreDtoList = genreService.findAll();

        // Verify
        assertEquals(expectedGenreDtoList, actualGenreDtoList);
    }
}