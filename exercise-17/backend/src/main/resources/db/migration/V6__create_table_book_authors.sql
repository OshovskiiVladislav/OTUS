CREATE TABLE book_authors
(
    book_id   bigint REFERENCES books (id) ON DELETE CASCADE,
    author_id bigint REFERENCES authors (id),
    PRIMARY KEY (book_id, author_id)
);
