-- !Ups

CREATE TABLE employee
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    email    VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL,
    status   VARCHAR(30) NOT NULL
);

INSERT INTO employee (email, password, status)
VALUES ('admin@mail.ru', '12345', 'Подтвержден');
INSERT INTO employee (email, password, status)
VALUES ('islam@mail.ru', '12345', 'Подтвержден');
INSERT INTO employee (email, password, status)
VALUES ('user@mail.ru', '12345', 'Подтвержден');