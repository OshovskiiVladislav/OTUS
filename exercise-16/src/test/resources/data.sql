INSERT INTO authors (name)
VALUES ('Dan Brown'),
       ('Scott Fitzgerald');

INSERT INTO genres (type)
VALUES ('Detective'),
       ('Roman');

INSERT INTO books (title)
VALUES ('Angels and Demons'),
       ('The Great Gatsby');

INSERT INTO comments (text, book_id)
VALUES ('Good book', 1),
       ('The best book', 2);

INSERT INTO book_authors (book_id, author_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO book_genres (book_id, genre_id)
VALUES (1, 1),
       (2, 2);
