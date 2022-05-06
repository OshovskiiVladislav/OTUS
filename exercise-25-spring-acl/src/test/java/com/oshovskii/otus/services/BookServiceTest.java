package com.oshovskii.otus.services;

import com.oshovskii.otus.dto.BookToSaveDto;
import com.oshovskii.otus.models.Author;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.models.Genre;
import com.oshovskii.otus.repositories.BookRepository;
import com.oshovskii.otus.services.implementations.BookServiceImpl;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.oshovskii.otus.factory.entity.TestEntityFactory.createBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(BookServiceImpl.class)
@DisplayName("BookService test")
class BookServiceTest {

    @SpyBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepositoryMock;

    @MockBean
    private MutableAclService mutableAclServiceMock;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("save() " +
            "with valid BookToSaveDto " +
            "should return saved book test")
    @WithMockUser(username="admin", roles={"EDITOR"})
    void save_withValidBookToSaveDto_shouldReturnSavedBook() {
        // Config
        val  genreId = 1L;
        val  authorId = 1L;

        val bookToSaveDto = new BookToSaveDto(
                "title",
                "author_name",
                "genre_name"
        );

        val expectedGenre = new Genre(genreId, bookToSaveDto.getGenre());
        val expectedAuthor = new Author(authorId, bookToSaveDto.getAuthor());

        val expectedBook = createBook(1);

        when(authorService.findByName(bookToSaveDto.getAuthor()))
                .thenReturn(expectedAuthor);

        when(genreService.findByName(bookToSaveDto.getGenre()))
                .thenReturn(expectedGenre);

        when(bookRepositoryMock.save(any(Book.class)))
                .thenReturn(expectedBook);

        when(mutableAclServiceMock.createAcl(any()))
                .thenReturn(new MutableAclTestImpl());

        // Call
        val actualBook = bookService.save(bookToSaveDto);

        // Verify
        assertEquals(expectedBook, actualBook);
        verify(bookRepositoryMock).save(any(Book.class));
    }

    @Test
    @DisplayName("findAll() " +
            "with void input " +
            "should return all books test")
    @WithMockUser(username="admin", roles={"EDITOR"})
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
    @DisplayName("findById() " +
            "with valid book id " +
            "should return expected book test")
    @WithMockUser(username="admin", roles={"EDITOR"})
    void findById_validBookId_shouldReturnBook() {
        //Config
        val bookId = 1L;
        val expectedBook = createBook(1);

        when(bookRepositoryMock.getById(bookId))
                .thenReturn(expectedBook);

        // Call
        val actualBook = bookService.findById(bookId);

        // Verify
        assertEquals(expectedBook, actualBook);
    }

    @Test
    @DisplayName("deleteById() " +
            "with valid book id " +
            "should delete book test")
    @WithMockUser(username="admin", roles={"EDITOR"})
    void deleteById_validBookId_shouldDeleteBook() {
        //Config
        val bookId = 1L;

        doNothing().when(bookRepositoryMock).deleteBookById(bookId);

        // Call
        bookService.deleteById(bookId);

        // Verify
        verify(bookRepositoryMock).deleteBookById(bookId);
    }

    private class MutableAclTestImpl implements MutableAcl {

        private Book expectedBook = createBook(1);

        @Override
        public void deleteAce(int i) throws NotFoundException {

        }

        @Override
        public Serializable getId() {
            return null;
        }

        @Override
        public void insertAce(int i, Permission permission, Sid sid, boolean b) throws NotFoundException {

        }

        @Override
        public void setOwner(Sid sid) {

        }

        @Override
        public void setEntriesInheriting(boolean b) {

        }

        @Override
        public void setParent(Acl acl) {

        }

        @Override
        public void updateAce(int i, Permission permission) throws NotFoundException {

        }

        @Override
        public List<AccessControlEntry> getEntries() {
            return new ArrayList<>();
        }

        @Override
        public ObjectIdentity getObjectIdentity() {
            return new ObjectIdentityImpl(expectedBook.getClass(), expectedBook.getId());
        }

        @Override
        public Sid getOwner() {
            return null;
        }

        @Override
        public Acl getParentAcl() {
            return null;
        }

        @Override
        public boolean isEntriesInheriting() {
            return false;
        }

        @Override
        public boolean isGranted(List<Permission> list, List<Sid> list1, boolean b) throws NotFoundException, UnloadedSidException {
            return false;
        }

        @Override
        public boolean isSidLoaded(List<Sid> list) {
            return false;
        }
    }
}
