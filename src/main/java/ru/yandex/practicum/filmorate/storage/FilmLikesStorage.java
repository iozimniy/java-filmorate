package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmLikesStorage {
    void addLikes(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);
    Collection<Film> getPopularFilms(Integer count);
}
