package com.oshovskii.otus.shell;

import com.oshovskii.otus.services.interfaces.BookService;
import com.oshovskii.otus.shell.interfaces.ShellBook;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class ShellBookImpl implements ShellBook {
    private final ShellLoginImpl shellLogin;
    private final BookService bookService;

    @Override
    @ShellMethod(value = "Publish count books command", key = {"countBook", "cB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public Long publishCountBooks() {
        return bookService.countBooks();
    }

    @Override
    @ShellMethod(value = "Publish book by id", key = {"getBookById", "getB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishBookByID(Long bookId) {
        return bookService.findByBookId(bookId).toString();
    }

    @Override
    @ShellMethod(value = "Publish all books", key = {"allBooks", "allB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishAllBook() {
        return bookService.findAllBooks().toString();
    }

    @Override
    @ShellMethod(value = "Publish book by title", key = {"findBookByTitle", "fbt"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishBookByTitle(String title) {
        return bookService.findByBookTitle(title).toString();
    }

    @Override
    @ShellMethod(value = "Save book", key = {"saveBookB", "saveB", "sB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String saveBook(Long authorId, Long genreId, Long commentId,String title) {
        bookService.saveBook(title, authorId, genreId, commentId);
        String completedCommandInsertBook = "Save book <"+ title + "> completed";
        return completedCommandInsertBook;
    }

    @Override
    @ShellMethod(value = "Delete book by id", key = {"deleteBook", "deleteB", "delB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String deleteBookByID(Long bookId) {
        bookService.deleteByBookId(bookId);
        String completedDeleteByIdInfo = "book with id " + bookId + " deleted";
        return completedDeleteByIdInfo;
    }

    @Override
    @ShellMethod(value = "Update book title by book id", key = {"updateBook", "updateB", "upB"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String updateTitleByBookId(Long id, String title) {
        bookService.updateTitleByBookId(id, title);
        String completedUpdateBookById = "book with id " + id + " updated";
        return completedUpdateBookById;
    }

    private Availability isPublishEventCommandAvailable() {
        return shellLogin.getCurrentUserName() == null ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}
