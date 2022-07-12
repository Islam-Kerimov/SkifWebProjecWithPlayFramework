-- Employees schema

-- !Ups

CREATE TABLE Employee
(
    id       bigint AUTO_INCREMENT NOT NULL,
    email    varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

-- !Downs

DROP TABLE Employee;