CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER PRIMARY KEY auto_increment,
    email VARCHAR(40) NOT NULL,
    login VARCHAR(20) NOT NULL,
    user_name VARCHAR(40),
    birthday DATE
);

CREATE TABLE IF NOT EXISTS friendship (
    user_id INTEGER REFERENCES users (user_id),
    friend_id INTEGER REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS genre (
    genre_id INTEGER PRIMARY KEY auto_increment,
    genre_name VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS rating (
    rating_id INTEGER PRIMARY KEY auto_increment,
    rating_name VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS films (
    film_id INTEGER PRIMARY KEY auto_increment,
    film_name VARCHAR(40),
    description VARCHAR(200),
    rating_id INTEGER REFERENCES rating (rating_id),
    release_date DATE,
    duration INTEGER
);

CREATE TABLE IF NOT EXISTS film_likes (
    film_id INTEGER REFERENCES films (film_id),
    user_id INTEGER REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id INTEGER REFERENCES films (film_id),
    genre_id INTEGER REFERENCES genre (genre_id)
);