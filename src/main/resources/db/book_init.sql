CREATE DATABASE quarkus_data;

USE quarkus_data;

CREATE TABLE book (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    pages INT NOT NULL
);

INSERT INTO book (name, pages) VALUES ("First Book", "John Doe", 155), ("Second Book", "Maria Doe", 303);