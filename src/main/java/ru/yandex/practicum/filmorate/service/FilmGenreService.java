package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

@AllArgsConstructor
public class FilmGenreService {
    private FilmGenreStorage filmGenreStorage;

    public void create(Film film) {
        film.getGenres().stream().forEach(id -> {
            filmGenreStorage.create(film.getId(), id);
        });
    }

    public void update(Film film) {
        delete(film.getId());
        create(film);
    }

    public void delete(Long filmId) {
        filmGenreStorage.delete(filmId);
    }
}
