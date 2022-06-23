CREATE TABLE comments
(
    id      BIGSERIAL,
    book_id BIGINT REFERENCES books (id) ON DELETE CASCADE,
    text    TEXT NOT NULL,
    PRIMARY KEY (id)
);
