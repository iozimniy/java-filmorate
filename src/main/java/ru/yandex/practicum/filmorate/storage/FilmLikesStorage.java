package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.sql.SQLException;
import java.util.Collection;

public interface FilmLikesStorage {
    void addLikes(Long filmId, Long userId) throws SQLException;

    void deleteLike(Long filmId, Long userId) throws SQLException;

    Collection<Film> getPopularFilms(Integer count);
}
