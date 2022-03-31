package com.oshovskii.otus.shell;

import com.oshovskii.otus.dto.BookDto;
import com.oshovskii.otus.services.interfaces.BookService;
import com.oshovskii.otus.shell.interfaces.ShellBook;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellBookImpl implements ShellBook {
    private final ShellLoginImpl shellLogin;
    private final BookService bookService;

    @Override
    @ShellMethod(value = "Publish count books command", key = {"countBook", "cB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public long publishCountBooks() {
        return bookService.countBooks();
    }

    @Override
    @ShellMethod(value = "Publish book by id", key = {"getBookById", "getB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public BookDto publishBookByID(Long bookId) {
        return bookService.findBookById(bookId);
    }

    @Override
    @ShellMethod(value = "Publish publish book by title ignore case", key = {"getByTitleIgnoreCase", "getBTI"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public BookDto publishBookByTitleIgnoreCase(String title) {
        return bookService.findBookByTitleIgnoreCase(title);
    }

    @Override
    @ShellMethod(value = "Publish all books", key = {"allBooks", "allB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public List<BookDto> publishAllBook() {
        return bookService.findAllBooks();
    }

    @Override
    @ShellMethod(value = "Publish book by title", key = {"findBookByTitle", "fbt"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public BookDto publishBookByTitle(String title) {
        return bookService.findBookByTitle(title);
    }

    @Override
    @ShellMethod(value = "Save book", key = {"saveBookB", "saveB", "sB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public BookDto saveBook(Long authorId, Long genreId, String title) {
        return bookService.saveBook(title, authorId, genreId);
    }

    @Override
    @ShellMethod(value = "Delete book by id", key = {"deleteBook", "deleteB", "delB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String deleteBookByID(Long bookId) {
        bookService.deleteBookById(bookId);
        String completedDeleteByIdInfo = "book with id " + bookId + " deleted";
        return completedDeleteByIdInfo;
    }

    private Availability isPublishEventCommandAvailable() {
        return shellLogin.getCurrentUserName() == null ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}
