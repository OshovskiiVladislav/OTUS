INSERT INTO authors (name)
VALUES ('Dan Brown'),
       ('Smith Den'),
       ('George R.R. Martin');

INSERT INTO genres (name)
VALUES ('Detective'),
       ('Roman'),
       ('Fantasy');

INSERT INTO books (author_id, genre_id, title)
VALUES (1, 1, 'Angels and Demons'),
       (1, 2, 'The da vinci code'),
       (1, 3, 'Game of Thrones');

INSERT INTO comments (book_id, note)
VALUES (1, 'The best'),
       (2, 'Amazing'),
       (3, 'Cool');

INSERT INTO users (id, username, password, full_name, age, email)
VALUES (1, 'admin', '$2a$10$YJM9Yq0rYD2kHPzr9.6e9OzbOScIIQIDzBrAAgmWVEk5tfvLOlSiS', 'Admin Admin', 17,
        'admin@mail.com'),
       (2, 'user', '$2a$10$YJM9Yq0rYD2kHPzr9.6e9OzbOScIIQIDzBrAAgmWVEk5tfvLOlSiS', 'User User', 25, 'user@mail.com');

INSERT INTO authority (id, user_id, authority)
VALUES (1, 2, 'ROLE_USER'),
       (2, 1, 'ROLE_EDITOR');

INSERT INTO acl_sid (id, principal, sid)
VALUES (1, 1, 'admin'), -- principal = 1 => user
       (2, 1, 'user'),  -- principal = 1 => user
       (3, 0, 'ROLE_EDITOR'); -- principal = 0 => role

INSERT INTO acl_class (id, class)
VALUES (1, 'com.oshovskii.otus.models.Book');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES (1, 1, 1, NULL, 3, 0),
       (2, 1, 2, NULL, 3, 0),
       (3, 1, 3, NULL, 3, 0);


-- acl_sid — кому дается разрешение;
-- acl_object_identity — на какой объект оно дается;
-- mask — какое именно разрешение. 1 —это READ, 2 — WRITE, 4 — CREATE… (см. класс BasePermission)
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (1, 1, 1, 1, 1, 1, 1, 1),
       (2, 1, 2, 1, 2, 1, 1, 1),
       (3, 1, 3, 3, 1, 1, 1, 1),
       (4, 2, 1, 2, 1, 1, 1, 1),
       (5, 2, 2, 3, 1, 1, 1, 1),
       (6, 3, 1, 3, 1, 1, 1, 1),
       (7, 3, 2, 3, 2, 1, 1, 1);

