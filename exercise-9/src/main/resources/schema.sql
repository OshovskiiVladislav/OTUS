CREATE TABLE authors (
    id BIGSERIAL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE genres (
    id BIGSERIAL,
    type VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE books (
    id BIGSERIAL,
    title VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE comments (
    id BIGSERIAL,
    book_id BIGINT REFERENCES books (id) ON DELETE CASCADE,
    text TEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE book_genres (
    book_id bigint REFERENCES books (id) ON DELETE CASCADE,
    genre_id bigint REFERENCES genres (id),
    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE book_authors (
    book_id bigint REFERENCES books (id) ON DELETE CASCADE,
    author_id bigint REFERENCES authors (id),
    PRIMARY KEY (book_id, author_id)
);