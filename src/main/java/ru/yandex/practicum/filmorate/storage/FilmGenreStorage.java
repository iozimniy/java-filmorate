package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface FilmGenreStorage {
    void create(List<FilmGenre> filmGenres) throws SQLException;

    void delete(Long filmId) throws SQLException;

    Collection<Genre> getFilmGenres(Long id);
}
