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


INSERT INTO users (id, username, password, full_name, email)
VALUES (1, 'user', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'User User', 'user@mail.com'),
       (2, 'admin', '$2a$10$YJM9Yq0rYD2kHPzr9.6e9OzbOScIIQIDzBrAAgmWVEk5tfvLOlSiS', 'Admin Admin', 'admin@mail.com');

INSERT INTO authority (id, user_id, authority)
VALUES (1, 1, 'ROLE_USER'),
       (2, 2, 'ROLE_ADMIN');
