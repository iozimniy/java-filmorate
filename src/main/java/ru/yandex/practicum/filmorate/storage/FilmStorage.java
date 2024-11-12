package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    Collection<Film> getFilms();

    Film create(Film film) throws SQLException;

    Film update(Film film) throws SQLException;

    boolean contains(Long id);

    Optional<Film> getFilmById(Long id);
}
