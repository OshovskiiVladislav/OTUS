DROP TABLE IF EXISTS genre CASCADE;
CREATE TABLE genre
(
    id   bigserial,
    name varchar(255) NOT NULL unique,
    CONSTRAINT genre_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS author CASCADE;
CREATE TABLE author
(
    id         bigserial,
    first_name varchar(255) NOT NULL,
    last_name  varchar(255) NOT NULL,
    CONSTRAINT author_pk PRIMARY KEY (id)
);


DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE book
(
    id        bigserial,
    name      varchar(255) NOT NULL,
    author_id bigint       NOT NULL,
    genre_id  bigint       NOT NULL,
    CONSTRAINT book_pk PRIMARY KEY (id),
    foreign key (author_id) REFERENCES author (id) on delete cascade,
    foreign key (genre_id) REFERENCES genre (id) on delete cascade
);

DROP TABLE IF EXISTS comment;
CREATE TABLE comment
(
    id      bigserial,
    text    varchar(255) NOT NULL,
    book_id bigint       NOT NULL,
    CONSTRAINT comment_pk PRIMARY KEY (id),
    foreign key (book_id) REFERENCES book (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS mongo_author;
CREATE TABLE mongo_author
(
    author_id bigserial,
    mongo_id  varchar(255) NOT NULL unique
);

DROP TABLE IF EXISTS mongo_genre;
CREATE TABLE mongo_genre
(
    genre_id bigserial,
    mongo_id varchar(255) NOT NULL unique
);

DROP TABLE IF EXISTS mongo_book;
CREATE TABLE mongo_book
(
    book_id  bigserial,
    mongo_id varchar(255) NOT NULL unique
);
