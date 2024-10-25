package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Comparator;

public interface FilmStorage {

    Collection<Film> getFilms();

    Film create(Film film);

    Film update(Film film);

    boolean contains(Long id);

    Film getFilmById(Long id);

    void addLikes(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    Collection<Film> getSortedFilms(Integer count, Comparator<Film> comparator);
}
