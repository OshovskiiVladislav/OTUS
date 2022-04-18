package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.models.Book;
import com.oshovskii.otus.services.interfaces.BookService;
import com.oshovskii.otus.shell.interfaces.ShellBook;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class ShellBookImpl implements ShellBook {
    private final ShellLoginImpl shellLogin;
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @Override
    @ShellMethod(value = "Publish book by id", key = {"getBookById", "getB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishBookByID(String bookId) {
        Book book = bookService.findById(bookId);
        return modelMapper.map(book, BookDto.class).toString();
    }

    @Override
    @ShellMethod(value = "Publish all books", key = {"allBooks", "allB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishAllBook() {
        return bookService.findAll().toString();
    }

    @Override
    @ShellMethod(value = "Publish book by title", key = {"findBookByTitle", "findByTitle", "fbt"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishBookByTitle(String title) {
        Book book = bookService.findByTitle(title);
        return modelMapper.map(book, BookDto.class).toString();
    }

    @Override
    @ShellMethod(value = "Publish book authors by book id", key = {"findAuthorsByBookId", "findByBookId", "fabi"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishBookAuthorsById(String bookId) {
        return bookService.findBookAuthorsById(bookId).toString();
    }

    @Override
    @ShellMethod(value = "Publish authors array length by book id", key = {"findAuthorsLengthByBookId", "findALById"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public long publishAuthorsArrayLengthByBookId(String bookId) {
        return bookService.findAuthorsArrayLengthByBookId(bookId);
    }

    @Override
    @ShellMethod(value = "Save book", key = {"saveBook", "saveB", "sB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String saveBook(String authorName, String genreType, String commentText, String title) {
        bookService.save(authorName, genreType, commentText, title);
        String completedCommandSaveBook = "Save book <"+ title + "> completed";
        return completedCommandSaveBook;
    }

    @Override
    @ShellMethod(value = "Save book", key = {"deleteAuthorsArrayElementsByBookId", "deleteAuthors", "dAL"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String deleteAuthorsArrayElementsByBookId(String bookId) {
        bookService.deleteAuthorsArrayElementsById(bookId);
        String completedCommandDeleteAuthorsListByBookId = "Delete authors list by book id: <"+ bookId + "> completed";
        return completedCommandDeleteAuthorsListByBookId;
    }

    private Availability isPublishEventCommandAvailable() {
        return shellLogin.getCurrentUserName() == null
                ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}
