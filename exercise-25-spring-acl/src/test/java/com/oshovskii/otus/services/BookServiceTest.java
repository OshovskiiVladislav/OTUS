package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.BookToSaveDto;
import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.services.implementations.BookServiceImpl;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.oshovskii.otus.factory.entity.TestEntityFactory.createBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(BookServiceImpl.class)
@DisplayName("BookService test")
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepositoryMock;

    @MockBean
    private MutableAclService mutableAclServiceMock;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

//    @Test
//    @DisplayName("save() " +
//            "with valid BookToSaveDto " +
//            "should return saved book test")
//    void save_withValidBookToSaveDto_shouldReturnSavedBook() {
//        // Config
//        val bookToSaveDto = new BookToSaveDto(
//                "title",
//                "author_name",
//                "genre_name"
//        );
//
//        when(authorService.findByName(bookToSaveDto.getAuthor()))
//                .thenReturn();
//
//        when(genreService.findByName(bookToSaveDto.getGenre()))
//                .thenReturn();
//        // Call
//
//        // Verify
//    }

    @Test
    @DisplayName("findAll() " +
            "with void input " +
            "should return all books test")
    void findAll_voidInput_shouldReturnAllBooks() {
        // Config
        val targetBook1 = createBook(1);
        val targetBook2 = createBook(2);

        val targetListBooks = List.of(targetBook1, targetBook2);

        when(bookRepositoryMock.findAll())
                .thenReturn(targetListBooks);

        // Call
        val actualListBooks = bookService.findAll();

        // Verify
        assertEquals(targetListBooks, actualListBooks);
    }

    @Test
    void findById() {
        // Config

        // Call

        // Verify
    }
}