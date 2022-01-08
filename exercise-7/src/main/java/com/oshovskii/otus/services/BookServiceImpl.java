package com.oshovskii.otus.services;

import com.oshovskii.otus.dao.interfaces.BookDao;
import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import com.oshovskii.otus.services.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    @Override
    public int countAllBook() {
        return bookDao.count();
    }

    @Override
    public void insertBook(Book book, Author author, Genre genre) {
        bookDao.insert(book, author, genre);
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAllBook() {
        return bookDao.getAll();
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteById(id);
    }
}
