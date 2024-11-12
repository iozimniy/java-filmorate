package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.SQLException;
import java.util.Collection;

public interface FilmGenreStorage {
    void create(Long filmId, Long genreId) throws SQLException;

    void delete(Long filmId) throws SQLException;

    Collection<Genre> getFilmGenres(Long id);
}
