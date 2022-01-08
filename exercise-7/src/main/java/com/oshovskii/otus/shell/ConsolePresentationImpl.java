package com.oshovskii.otus.shell;

import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import com.oshovskii.otus.services.interfaces.BookService;
import com.oshovskii.otus.shell.interfaces.ConsolePresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ConsolePresentationImpl implements ConsolePresentation {
    private final BookService bookService;
    private String userName;

    @Override
    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        return String.format("Добро пожаловать: %s", userName);
    }

    @Override
    @ShellMethod(value = "Publish count books command", key = {"count", "c"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public int publishCountBooks() {
        return bookService.countAllBook();
    }

    @Override
    @ShellMethod(value = "Publish book by id", key = {"getById", "get"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishBookByID(Long bookId) {
        return bookService.getBookById(bookId).toString();
    }

    @Override
    @ShellMethod(value = "Publish all books", key = {"allBooks", "all"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishAllBook() {
        return bookService.getAllBook().toString();
    }

    @Override
    @ShellMethod(value = "Insert book", key = {"insertBook", "insert", "ins"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String insetBook(String title, String author_name, String genre_name) {
        Book book = new Book(title);
        Author author = new Author(author_name);
        Genre genre = new Genre(genre_name);

        bookService.insertBook(book, author, genre);
        String completedCommandInsertBook = "insert book <"+ book.getTitle() + "> completed";
        return completedCommandInsertBook;
    }

    @Override
    @ShellMethod(value = "Delete book by id", key = {"deleteBook", "delete", "del"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String deleteBookByID(Long bookId) {
        bookService.deleteBookById(bookId);
        String completedDeleteByIdInfo = "book with id " + bookId + " deleted";
        return completedDeleteByIdInfo;
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null? Availability.unavailable("Сначала залогиньтесь"): Availability.available();
    }
}
