INSERT INTO genre(genre_name) VALUES ('Комедия');
INSERT INTO genre(genre_name) VALUES ('Драма');
INSERT INTO genre(genre_name) VALUES ('Мультфильм');
INSERT INTO genre(genre_name) VALUES ('Триллер');
INSERT INTO genre(genre_name) VALUES ('Документальный');
INSERT INTO genre(genre_name) VALUES ('Боевик');

INSERT INTO rating(rating_name) VALUES ('G');
INSERT INTO rating(rating_name) VALUES ('PG');
INSERT INTO rating(rating_name) VALUES ('PG-13');
INSERT INTO rating(rating_name) VALUES ('R');
INSERT INTO rating(rating_name) VALUES ('NC-17');

INSERT INTO films(film_name, description, rating_id, release_date, duration)
VALUES('Фильм', 'Описание фильма', '2', '2024-01-01', '120');

INSERT INTO films(film_name, description, rating_id, release_date, duration)
VALUES('Фильм 2', 'Описание фильма 2', '3', '2017-01-19', '126');