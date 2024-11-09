package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmGenreStorage {
    void create(Long filmId, Long genreId);
    void delete(Long filmId);
}
