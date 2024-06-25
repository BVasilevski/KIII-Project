CREATE DATABASE IF NOT EXISTS movies;

USE movies;

CREATE TABLE IF NOT EXISTS movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    director VARCHAR(255),
    genre VARCHAR(255)
);

INSERT INTO movies (name, director, genre) VALUES
    ('Movie 1', 'Director 1', 'Sci-Fi'),
    ('Movie 2', 'Director 2', 'Action');
