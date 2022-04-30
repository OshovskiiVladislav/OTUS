package com.oshovskii.otus.batch;

import com.oshovskii.otus.models.sql.Book;
import com.oshovskii.otus.models.sql.Comment;
import com.oshovskii.otus.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CommentItemWriter<T> extends JpaItemWriter<T> {

    private final BookRepository bookRepository;

    @Override
    public void write(List list) {
        List<Comment> comments = new ArrayList<>(list);
        Map<String, Book> books = findBooks(comments);
        comments.forEach(comment -> comment.setBook(books.get(comment.getBook().getMongoId())));

        super.write(list);
    }

    @Transactional(readOnly = true)
    public Map<String, Book> findBooks(List<Comment> comments) {
        Map<String, Book> books = new HashMap<>();
        comments.forEach(comment -> books.put(comment.getBook().getMongoId(), comment.getBook()));

        bookRepository.findByMongoIdIn(books.keySet())
                .forEach(book -> books.put(book.getMongoId(), book));

        return books;
    }
}
