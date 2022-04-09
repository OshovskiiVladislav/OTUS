INSERT INTO author(name)
VALUES ('Михаил Булгаков'),
       ('Антуан де Сент-Экзюпери'),
       ('Александр Дюма'),
       ('Аркадий и Борис Стругацкие');

INSERT INTO genre (name)
VALUES ('Роман'),
       ('Проза'),
       ('Фантастика'),
       ('Приключения');

INSERT INTO book(author_id, genre_id, title)
VALUES (1, 1, 'Мастер и Маргарита'),
       (1, 2, 'Белая гвардия'),
       (1, 3, 'Собачье сердце'),
       (2, 2, 'Маленький принц'),
       (3, 4, 'Граф Монте-Кристо'),
       (4, 3, 'Трудно быть Богом'),
       (4, 3, 'Пикник на обочине'),
       (4, 3, 'Улитка на склоне'),
       (4, 3, 'Обитаемый остров'),
       (4, 3, 'Отель у погибшего альпениста');

INSERT INTO note(book_id, note)
VALUES (1, 'Note-01.1 - Мастер'),
       (1, 'Note-01.2 - Мастер'),
       (2, 'Note-02.1 - гвардия'),
       (2, 'Note-02.2 - гвардия'),
       (3, 'Note-03.1 - сердце'),
       (3, 'Note-03.2 - сердце'),
       (3, 'Note-03.3 - сердце'),
       (4, 'Note-04.1 - M принц'),
       (4, 'Note-04.2 - M принц'),
       (5, 'Note-05.1 - Граф MK'),
       (5, 'Note-05.2 - Граф MK'),
       (5, 'Note-05.3 - Граф MK'),
       (6, 'Note-06.1 - Трудно'),
       (6, 'Note-06.2 - Трудно'),
       (7, 'Note-07.1 - Пикник'),
       (7, 'Note-07.2 - Пикник'),
       (7, 'Note-07.3 - Пикник'),
       (8, 'Note-08.1 - Улитка'),
       (8, 'Note-08.2 - Улитка'),
       (8, 'Note-08.3 - Улитка'),
       (8, 'Note-08.4 - Улитка'),
       (9, 'Note-09.1 - остров'),
       (9, 'Note-09.1 - остров'),
       (10, 'Note-10.1 - Отель'),
       (10, 'Note-10.2 - Отель'),
       (10, 'Note-10.3 - Отель'),
       (10, 'Note-10.4 - Отель');


INSERT INTO users (id, username, password, full_name, email)
VALUES (1, 'user', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'User User', 'user@mail.com'),
       (2, 'admin', '$2a$10$YJM9Yq0rYD2kHPzr9.6e9OzbOScIIQIDzBrAAgmWVEk5tfvLOlSiS', 'Admin Admin', 'admin@mail.com');

INSERT INTO authority (id, user_id, authority)
VALUES (1, 1, 'ROLE_USER'),
       (2, 2, 'ROLE_ADMIN');
