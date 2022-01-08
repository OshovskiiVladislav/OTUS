package com.oshovskii.otus.dao;

import com.oshovskii.otus.dao.interfaces.BookDao;
import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final ResultSetExtractor<List<Book>> mapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(Book.class);
    private static final String SQL_INSERT_BOOK = "INSERT INTO books (title) VALUES (:title)";
    private static final String SQL_INSERT_AUTHOR = "INSERT INTO authors (name) VALUES (:name)";
    private static final String SQL_INSERT_GENRE = "INSERT INTO genres (name) VALUES (:name)";
    private static final String SQL_GET_BOOK_BY_ID = "SELECT b.id as id, b.title,"
            + " a.id as author_id, a.name as author_name, "
            + " g.id as genre_id, g.name as genre_name"
            + " FROM books b"
            + " LEFT OUTER JOIN authors a ON a.id = b.id"
            + " LEFT OUTER JOIN genres g ON g.id = b.id"
            + " WHERE b.id = :id";
    private static final String SQL_GET_ALL_BOOK =
            "SELECT b.id as id, b.title as title,"
            + " a.id as author_id, a.name as author_name, "
            + " g.id as genre_id, g.name as genre_name"
            + " FROM books b"
            + " LEFT JOIN authors a ON (b.id = a.id)"
            + " LEFT OUTER JOIN genres g ON (b.id = g.id)";
    private static final String SQL_DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = :id";

    @Override
    public int count() {
        String sql = "SELECT count(*) FROM books ";
        Integer count = namedParameterJdbcOperations.getJdbcOperations().queryForObject(sql, Integer.class);
        return count == null ? 0 : count;
    }

    @Transactional
    @Override
    public void insert(Book book, Author author, Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource bookParameters = new MapSqlParameterSource()
                .addValue("title", book.getTitle());

        namedParameterJdbcOperations.update(SQL_INSERT_BOOK, bookParameters, keyHolder);

        SqlParameterSource authorParameters = new MapSqlParameterSource()
                .addValue("name", author.getName());

        namedParameterJdbcOperations.update(SQL_INSERT_AUTHOR, authorParameters, keyHolder);

        SqlParameterSource genreParameters = new MapSqlParameterSource()
                .addValue("name", genre.getName());

        namedParameterJdbcOperations.update(SQL_INSERT_GENRE, genreParameters, keyHolder);
    }

    @Override
    public Book getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> books = namedParameterJdbcOperations.query(SQL_GET_BOOK_BY_ID, params, mapper);
        return Objects.requireNonNull(books).get(books.size() - 1);
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(SQL_GET_ALL_BOOK, mapper);
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(SQL_DELETE_BOOK_BY_ID, params);
    }
}
