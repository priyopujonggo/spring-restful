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