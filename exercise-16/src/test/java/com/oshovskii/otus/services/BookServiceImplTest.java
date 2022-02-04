package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.repositories.BookRepository;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static com.oshovskii.otus.factory.TestBookDtoFactory.createBookDtoWithAllInfoById;
import static com.oshovskii.otus.factory.TestBookFactory.createBookWithAllInfoById;
import static com.oshovskii.otus.utils.Utils.EXISTING_BOOK_ID;
import static com.oshovskii.otus.utils.Utils.EXISTING_BOOK_ID_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("BookServiceImpl Test")
@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private ModelMapper modelMapperMock;

    @DisplayName("Return expected list books test")
    @Test
    void findAllBooks_voidInput_shouldReturnExpectedBooksList() {
        // Config
        // create 1 book
        val expectedBook = createBookWithAllInfoById(EXISTING_BOOK_ID);
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        // create 2 book
        val expectedBook2 = createBookWithAllInfoById(EXISTING_BOOK_ID_2);
        val expectedBookDto2 = createBookDtoWithAllInfoById(EXISTING_BOOK_ID_2);

        val expectedBookList = List.of(expectedBook, expectedBook2);
        val expectedBookDtoList = List.of(expectedBookDto, expectedBookDto2);

        when(bookRepository.findAll()).thenReturn(expectedBookList);
        when(modelMapperMock.map(expectedBook, BookDto.class)).thenReturn(expectedBookDto);
        when(modelMapperMock.map(expectedBook2, BookDto.class)).thenReturn(expectedBookDto2);

        // Call
        val actualBookDtoList = bookService.findAllBooks();

        // Verify
        assertEquals(actualBookDtoList, expectedBookDtoList);
    }

    @DisplayName("Return expected book by id test")
    @Test
    void findBookById_validBookId_shouldReturnExpectedBookById(){
        // Config
        val expectedBook = createBookWithAllInfoById(EXISTING_BOOK_ID);
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        when(bookRepository.findById(EXISTING_BOOK_ID)).thenReturn(Optional.of(expectedBook));
        when(modelMapperMock.map(expectedBook, BookDto.class)).thenReturn(expectedBookDto);

        // Call
        val actualBookDto = bookService.findBookById(expectedBook.getId());

        // Verify
        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @DisplayName("Save book test")
    @Test
    void save_validTitleAndAuthorIdAndGenreIdAndCommentId_shouldSaveBook(){
        // Config;
        val savedBook = createBookWithAllInfoById(EXISTING_BOOK_ID);
        val expectedBookDto = createBookDtoWithAllInfoById(EXISTING_BOOK_ID);

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(modelMapperMock.map(expectedBookDto, Book.class)).thenReturn(savedBook);
        when(modelMapperMock.map(savedBook, BookDto.class)).thenReturn(expectedBookDto);

        // Call
        val actualBookDto = bookService.saveBook(expectedBookDto);

        // Verify
        assertEquals(expectedBookDto, actualBookDto);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Delete book by id test")
    @Test
    void deleteBookById_validId_shouldCorrectDeleteBookById(){
        // Config
        doNothing().when(bookRepository).deleteById(EXISTING_BOOK_ID);

        // Call
        bookService.deleteBookById(EXISTING_BOOK_ID);

        // Verify
        verify(bookRepository, times(1)).deleteById(EXISTING_BOOK_ID);
    }
}
