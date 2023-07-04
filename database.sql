CREATE DATABASE spring_restful_api;

USE spring_restful_api;
CREATE TABLE users (
    email            VARCHAR(100) NOT NULL ,
    password         VARCHAR(100) NOT NULL , 
    name             VARCHAR(100) NOT NULL ,
    token            VARCHAR(100),
    token_expired_at BIGINT,
    PRIMARY KEY (email),
    UNIQUE (token) 
) ENGINE InnoDB;

SELECT * FROM users;

DESC users;

CREATE TABLE logs (
  id VARCHAR(100) PRIMARY KEY,
  email VARCHAR(100) NOT NULL,
  verse VARCHAR(100) NOT NULL,
  timestamp TIMESTAMP
)ENGINE InnoDB;

CREATE TABLE contacts
(
    id         VARCHAR(100) NOT NULL,
    username   VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100),
    phone      VARCHAR(100),
    email      VARCHAR(100),
    PRIMARY KEY (id),
    FOREIGN KEY fk_users_contacts (username) REFERENCES users (email)
) ENGINE InnoDB;

SELECT *
FROM contacts;

DESC contacts;

CREATE TABLE addresses
(
    id          VARCHAR(100) NOT NULL,
    contact_id  VARCHAR(100) NOT NULL,
    street      VARCHAR(200),
    city        VARCHAR(100),
    province    VARCHAR(100),
    country     VARCHAR(100) NOT NULL,
    postal_code VARCHAR(10),
    PRIMARY KEY (id),
    FOREIGN KEY fk_contacts_addresses (contact_id) REFERENCES contacts (id)
) ENGINE InnoDB;

SELECT * FROM addresses;

DESC addresses;

CREATE TABLE questions
(
    id               VARCHAR(100) NOT NULL,
    category         VARCHAR(100),
    difficulty_level VARCHAR(100),
    option1          VARCHAR(200),
    option2          VARCHAR(200),
    option3          VARCHAR(200),
    option4          VARCHAR(200),
    question_title   VARCHAR(200),
    right_answer     VARCHAR(200),
    PRIMARY KEY (id)
) ENGINE InnoDB;

SELECT * FROM questions;
DESC questions;

DELETE FROM questions;

DELETE FROM addresses;

DELETE FROM contacts;

DELETE FROM users;