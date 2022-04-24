package com.oshovskii.otus.services.implementations;

import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.services.BookService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.oshovskii.otus.factory.BookFactory.createBook;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("BookServiceImpl test")
@ExtendWith(SpringExtension.class)
@Import(BookServiceImpl.class)
class BookServiceImplTest {

    @SpyBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void findAll_voidInput_shouldReturnListBook() {
        //Config
        val book1 = createBook(1);
        val book2 = createBook(2);

        val expectedBookList = List.of(book1, book2);

        when(bookRepository.findAll()).thenReturn(expectedBookList);

        // Call
        val actualBookList = bookService.findAll();

        // Verify
        assertEquals(expectedBookList, actualBookList);
    }

    @Test
    void findById_validBookId_shouldReturnBook() {
        //Config
        val bookId = 1L;
        val expectedBook = createBook(1);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));

        // Call
        val actualBook = bookService.findById(bookId);

        // Verify
        assertEquals(expectedBook, actualBook);
    }

    @Test
    void save_validBook_shouldSaveBookAndReturnBook() {
        //Config
        val sourceBook = createBook(1);
        val expectedBook = createBook(1);

        when(bookRepository.save(sourceBook)).thenReturn(expectedBook);

        // Call
        val actualBook = bookService.save(sourceBook);

        // Verify
        assertEquals(expectedBook, actualBook);
    }

    @Test
    void deleteById_validBookId_shouldDeleteBook() {
        //Config
        val bookId = 1L;

        doNothing().when(bookRepository).deleteById(bookId);

        // Call
        bookService.deleteById(bookId);

        // Verify
        verify(bookRepository).deleteById(bookId);
    }
}
