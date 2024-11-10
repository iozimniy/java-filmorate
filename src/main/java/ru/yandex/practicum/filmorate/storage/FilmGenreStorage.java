package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface FilmGenreStorage {
    void create(Long filmId, Long genreId);

    void delete(Long filmId);

    Collection<Genre> getFilmGenres(Long id);
}
