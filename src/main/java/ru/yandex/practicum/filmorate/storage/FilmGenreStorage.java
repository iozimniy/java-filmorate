package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface FilmGenreStorage {
    void create(Long filmId, Long genreId);
    void delete(Long filmId);
    Collection<Genre> getFilmGenresId(Long id);
}
