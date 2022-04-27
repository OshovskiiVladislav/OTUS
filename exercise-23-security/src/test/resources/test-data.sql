INSERT INTO author(name)
VALUES ('Dan Brown'),
       ('Francis Scott Fitzgerald'),
       ('George Raymond Richard Martin'),
       ('Ayn Rand');

INSERT INTO genre (name)
VALUES ('Roman'),
       ('Detective'),
       ('Fantasy'),
       ('Philosophy');

INSERT INTO book(author_id, genre_id, title)
VALUES (1, 1, 'THE DA VINCI CODE'),
       (1, 2, 'Inferno'),
       (1, 3, 'Angels & Demons'),
       (2, 1, 'The Great Gatsby'),
       (3, 1, 'A Game of Thrones'),
       (4, 3, 'Atlas Shrugged'),
       (4, 1, 'The Fountainhead'),
       (1, 3, 'We the Living');

INSERT INTO note(book_id, note)
VALUES (1, 'Note-1.1 - A'),
       (1, 'Note-1.2 - B'),
       (2, 'Note-2.1 - C'),
       (2, 'Note-2.2 - D'),
       (3, 'Note-3.1 - E'),
       (3, 'Note-3.2 - F'),
       (3, 'Note-3.3 - G'),
       (4, 'Note-4.1 - H'),
       (4, 'Note-4.2 - I'),
       (5, 'Note-5.1 - J'),
       (5, 'Note-5.2 - K'),
       (5, 'Note-5.3 - L'),
       (6, 'Note-6.1 - M'),
       (6, 'Note-6.2 - N'),
       (7, 'Note-7.1 - O'),
       (7, 'Note-7.2 - P'),
       (7, 'Note-7.3 - Q'),
       (8, 'Note-8.1 - R'),
       (8, 'Note-8.2 - S'),
       (8, 'Note-8.3 - T'),
       (8, 'Note-8.4 - U');
