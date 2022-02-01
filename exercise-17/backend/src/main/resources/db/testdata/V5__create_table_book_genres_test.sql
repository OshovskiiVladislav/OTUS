CREATE TABLE book_genres
(
    book_id  bigint REFERENCES books (id) ON DELETE CASCADE,
    genre_id bigint REFERENCES genres (id),
    PRIMARY KEY (book_id, genre_id)
);
