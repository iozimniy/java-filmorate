INSERT INTO films(film_name, description, rating_id, release_date, duration)
VALUES('Фильм', 'Описание фильма', 2, '2024-01-01', 120);

INSERT INTO films(film_name, description, rating_id, release_date, duration)
VALUES('Фильм 2', 'Описание фильма 2', 3, '2017-01-19', 126);

INSERT INTO users(email, login, user_name, birthday)
VALUES('test@test.com', 'user1', 'Пользователь 1', '1990-01-01');

INSERT INTO users(email, login, user_name, birthday)
VALUES('test1@test.com', 'user2', 'Пользователь 2', '1997-12-01');

INSERT INTO users(email, login, user_name, birthday)
VALUES('test10@test.com', 'user10', 'Пользователь 10', '1992-08-05');

INSERT INTO users(email, login, user_name, birthday)
VALUES('friend@test.com', 'friend', 'Друг', '1991-10-17');

INSERT INTO friendship(user_id, friend_id)
VALUES(1, 2);

INSERT INTO friendship(user_id, friend_id)
VALUES(1, 3);

INSERT INTO friendship(user_id, friend_id)
VALUES(2, 3);