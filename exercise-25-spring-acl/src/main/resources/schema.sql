DROP TABLE IF EXISTS authors CASCADE;
CREATE TABLE authors
(
    id   BIGSERIAL,
    name CHARACTER VARYING(255),
    CONSTRAINT author_pk PRIMARY KEY (id)
);


DROP TABLE IF EXISTS genres CASCADE;
CREATE TABLE genres
(
    id   BIGSERIAL,
    name CHARACTER VARYING(50),
    CONSTRAINT genre_pk PRIMARY KEY (id)
);


DROP TABLE IF EXISTS books CASCADE;
CREATE TABLE books
(
    id        BIGSERIAL,
    author_id BIGINT NOT NULL,
    genre_id  BIGINT NOT NULL,
    title     CHARACTER VARYING(50),
    CONSTRAINT book_pk PRIMARY KEY (id)
);

ALTER TABLE books
    ADD CONSTRAINT book_fk_01 FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE;
ALTER TABLE books
    ADD CONSTRAINT book_fk_02 FOREIGN KEY (genre_id) REFERENCES genres (id) ON DELETE CASCADE;

DROP TABLE IF EXISTS comments CASCADE;
CREATE TABLE comments
(
    id      BIGSERIAL,
    book_id BIGINT NOT NULL,
    note    CHARACTER VARYING(255),
    CONSTRAINT note_pk PRIMARY KEY (id)
);
ALTER TABLE comments
    ADD CONSTRAINT book_note_fk_01 FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE;


CREATE TABLE IF NOT EXISTS acl_sid (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  principal tinyint(1) NOT NULL,
  sid varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_1 (sid,principal)
);

CREATE TABLE IF NOT EXISTS acl_class (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_2 (class)
);

CREATE TABLE IF NOT EXISTS acl_entry (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  acl_object_identity bigint(20) NOT NULL,
  ace_order int(11) NOT NULL,
  sid bigint(20) NOT NULL,
  mask int(11) NOT NULL,
  granting tinyint(1) NOT NULL,
  audit_success tinyint(1) NOT NULL,
  audit_failure tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  object_id_class bigint(20) NOT NULL,
  object_id_identity bigint(20) NOT NULL,
  parent_object bigint(20) DEFAULT NULL,
  owner_sid bigint(20) DEFAULT NULL,
  entries_inheriting tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id                      BIGSERIAL,
    username                CHARACTER VARYING(50)  NOT NULL,
    password                CHARACTER VARYING(100) NOT NULL,
    full_name               CHARACTER VARYING(100),
    age                     INTEGER,
    email                   CHARACTER VARYING(100),
    enabled                 boolean                NOT NULL DEFAULT true,
    account_non_expired     boolean                NOT NULL DEFAULT true,
    account_non_locked      boolean                NOT NULL DEFAULT true,
    credentials_non_expired boolean                NOT NULL DEFAULT true,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

drop table if exists authority;
CREATE TABLE authority
(
    id        BIGSERIAL,
    user_id   BIGINT                NOT NULL,
    authority CHARACTER VARYING(50) NOT NULL
);

ALTER TABLE authority
    ADD CONSTRAINT authority_fk_01 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

CREATE UNIQUE INDEX ix_auth_user ON authority (user_id, authority);
